package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.VehiculoDAO;
import pe.edu.pe.parking.model.Vehiculo;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ActualizarVehiculo", urlPatterns = {"/ActualizarVehiculo"})
public class ActualizarVehiculoServlet extends HttpServlet {

    private VehiculoDAO vehiculoDAO;

    @Override
    public void init() throws ServletException {
        try {
            vehiculoDAO = new VehiculoDAO();
        } catch (SQLException | NamingException e) {
            throw new ServletException("Error inicializando VehiculoDAO: " + e.getMessage(), e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numeroPlaca = request.getParameter("numero_placa");
        String tipoVehiculo = request.getParameter("tipo_vehiculo");
        String marca = request.getParameter("marca");
        String color = request.getParameter("color");
        String dni_conductor = request.getParameter("dni_conductor");

        if (numeroPlaca != null && marca != null && tipoVehiculo != null && color != null) {
            try {
                // Objeto Vehiculo
                Vehiculo vehiculo = new Vehiculo.Builder(numeroPlaca, Vehiculo.Tipo_Vehiculo.valueOf(tipoVehiculo), dni_conductor)
                        .withMarca(marca)
                        .withColor(color)
                        .build();

                // Actualización del vehículo
                if (vehiculoDAO.actualizarVehiculo(vehiculo)) {
                    response.sendRedirect(request.getContextPath() + "/vehiculo");
                } else {
                    logError("No se pudo actualizar el vehículo con placa: " + numeroPlaca, request, response);
                }
            } catch (IllegalArgumentException e) {
                logError("Datos inválidos o error en la actualización: " + e.getMessage(), request, response);
            }
        } else {
            logError("Datos requeridos faltantes para la actualización del vehículo", request, response);
        }
    }

    private void logError(String message, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ErrorLog.log(message, ErrorLog.Level.ERROR);
        request.setAttribute("error", message);
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        try {
            vehiculoDAO.close();
        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al cerrar VehiculoDAO: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
