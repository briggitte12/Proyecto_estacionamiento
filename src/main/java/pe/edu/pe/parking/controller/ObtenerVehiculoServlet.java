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

@WebServlet(name = "ObtenerVehiculo", urlPatterns = {"/ObtenerVehiculo"})
public class ObtenerVehiculoServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numeroPlaca = request.getParameter("numeroPlaca");

        if (numeroPlaca != null) {
            try {
                Vehiculo vehiculo = vehiculoDAO.obtenerVehiculo(numeroPlaca);
                if (vehiculo != null) {
                    request.setAttribute("vehiculo", vehiculo);
                    request.getRequestDispatcher("editarVehiculo.jsp").forward(request, response);
                } else {
                    logError("Vehículo no encontrado para la placa: " + numeroPlaca, request, response);
                }
            } catch (Exception e) {
                logError("Error al obtener el vehículo: " + e.getMessage(), request, response);
            }
        } else {
            logError("Número de placa es requerido pero no fue proporcionado", request, response);
        }
    }

    private void logError(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
