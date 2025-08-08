package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.ConductorDAO;
import pe.edu.pe.parking.business.VehiculoDAO;
import pe.edu.pe.parking.model.Conductor;
import pe.edu.pe.parking.model.Vehiculo;
import pe.edu.pe.parking.util.ErrorLog;
import pe.edu.pe.parking.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "vehiculoConductor", urlPatterns = {"/vehiculoConductor"})
public class RegistroVehiculoConductor extends HttpServlet {

    private ConductorDAO conductorDAO;
    private VehiculoDAO vehiculoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            conductorDAO = new ConductorDAO();
            vehiculoDAO = new VehiculoDAO();
        } catch (Exception e) {
            try {
                ErrorLog.log("Error al inicializar DAOs: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new ServletException("Error al inicializar DAO", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Conductor conductor = (Conductor) session.getAttribute("conductorRegistrado");

        // Verificar si el conductor es nulo y manejar el registro
        if (conductor == null) {
            String dni_conductor = req.getParameter("dniConductor");
            String nombre_completo = req.getParameter("nombreCompleto");
            String celular = req.getParameter("celular");
            String correo = req.getParameter("correo");
            String fecha_nacimiento = req.getParameter("fechaNacimiento");
            String direccion = req.getParameter("direccion");
            String generoString = req.getParameter("genero");

            // Validar datos del conductor
            try {
                Validar.validarDatosConductor(dni_conductor, nombre_completo, celular, correo, fecha_nacimiento);
                Conductor.Genero genero = Conductor.Genero.valueOf(generoString);
                conductor = new Conductor.Builder(dni_conductor, nombre_completo, celular, correo, LocalDate.parse(fecha_nacimiento), genero)
                        .withdireccion(direccion).build();

                conductorDAO.RegistrarConductor(conductor);
                session.setAttribute("conductorRegistrado", conductor);
                ErrorLog.log("Conductor registrado: " + dni_conductor, ErrorLog.Level.INFO);
            } catch (ServletException e) {
                logError("Error al registrar conductor: " + e.getMessage());
                throw e;
            }
        }

        // registro del vehículo
        String tipo_vehiculoString = req.getParameter("tipoVehiculo");
        String numero_placa = req.getParameter("numeroPlaca");
        String marca = req.getParameter("marca");
        String color = req.getParameter("color");

        // Validar datos del vehículo
        try {
            Validar.validarDatosVehiculo(numero_placa, marca, color);
            Vehiculo.Tipo_Vehiculo tipoVehiculo = Vehiculo.Tipo_Vehiculo.valueOf(tipo_vehiculoString);
            Vehiculo vehiculo = new Vehiculo.Builder(numero_placa, tipoVehiculo, conductor.getDni_conductor())
                    .withMarca(marca)
                    .withColor(color)
                    .build();

            vehiculoDAO.RegistrarVehiculo(vehiculo);
            ErrorLog.log("Vehículo registrado: " + numero_placa, ErrorLog.Level.INFO);

            String add_vehiculo = req.getParameter("addVehiculo");
            if ("si".equalsIgnoreCase(add_vehiculo)) {
                req.setAttribute("conductor", conductor);
                req.getRequestDispatcher("add_vehiculo_conductor.jsp").forward(req, resp);
            } else {
                session.removeAttribute("conductorRegistrado");
                req.setAttribute("conductor", conductor);
                req.setAttribute("vehiculo", vehiculo);
                req.getRequestDispatcher("/conductor").forward(req, resp);
            }
        } catch (ServletException e) {
            logError("Error al registrar vehículo: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void destroy() {
        // Cerrar recursos y conexiones
        if (conductorDAO != null) {
            try {
                conductorDAO.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar ConductorDAO: " + e.getMessage());
            }
        }
        if (vehiculoDAO != null) {
            try {
                vehiculoDAO.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar VehiculoDAO: " + e.getMessage());
            }
        }
        super.destroy();
    }

    private void logError(String message) {
        try {
            ErrorLog.log(message, ErrorLog.Level.ERROR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
