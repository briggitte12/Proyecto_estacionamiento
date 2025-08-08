package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.ConductorDAO;
import pe.edu.pe.parking.business.VehiculoDAO;
import pe.edu.pe.parking.model.Vehiculo;
import pe.edu.pe.parking.util.ErrorLog;
import pe.edu.pe.parking.util.Validar;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "registroVehiculoSolo", urlPatterns = {"/registroVehiculoSolo"})
public class RegistroVehiculoSolo extends HttpServlet {

    private ConductorDAO conductorDAO;
    private VehiculoDAO vehiculoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            conductorDAO = new ConductorDAO();
            vehiculoDAO = new VehiculoDAO();
        } catch (SQLException | NamingException e) {
            throw new ServletException("Error al inicializar los DAOs: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dniConductor = req.getParameter("dniConductor");
        String numeroPlaca = req.getParameter("numeroPlaca");
        String marca = req.getParameter("marca");
        String tipoVehiculoString = req.getParameter("tipoVehiculo");
        String color = req.getParameter("color");

        try {
            validarCampos(dniConductor, numeroPlaca, marca, tipoVehiculoString, color);
            if (!conductorDAO.dniExistente(dniConductor)) {
                throw new IllegalArgumentException("Conductor no encontrado.");
            }

            Vehiculo vehiculo = new Vehiculo.Builder(numeroPlaca, Vehiculo.Tipo_Vehiculo.valueOf(tipoVehiculoString), dniConductor)
                    .withMarca(marca)
                    .withColor(color)
                    .build();

            vehiculoDAO.RegistrarVehiculo(vehiculo);
            req.setAttribute("vehiculo", vehiculo);
            req.getRequestDispatcher("/vehiculo").forward(req, resp);
        } catch (IllegalArgumentException | SQLException e) {
            logError(e.getMessage());
            req.setAttribute("mensaje", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        } catch (Exception e) {
            logError("Error inesperado: " + e.getMessage());
            req.setAttribute("mensaje", "Error inesperado: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        if (conductorDAO != null) {
            try {
                conductorDAO.close();
            } catch (SQLException e) {
                logError("Error al cerrar ConductorDAO: " + e.getMessage());
            }
        }
        if (vehiculoDAO != null) {
            try {
                vehiculoDAO.close();
            } catch (SQLException e) {
                logError("Error al cerrar VehiculoDAO: " + e.getMessage());
            }
        }
        super.destroy();
    }

    private void validarCampos(String dni, String placa, String marca, String tipo, String color) throws ServletException {
        if (dni.isEmpty() || placa.isEmpty() || marca.isEmpty() || tipo.isEmpty() || color.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }
        Validar.validarDatosVehiculo(placa, marca, color);
    }

    private void logError(String message) {
        try {
            ErrorLog.log(message, ErrorLog.Level.ERROR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
