package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.Ingreso_VehiculosDAO;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet(name = "ActualizarFechaSalidaServlet", urlPatterns = {"/ActualizarFechaSalidaServlet"})
public class ActualizarFechaSalidaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idRegistro = request.getParameter("id");
        Ingreso_VehiculosDAO ingresoDAO = null;

        try {
            ingresoDAO = new Ingreso_VehiculosDAO();
            LocalDateTime fechaSalida = LocalDateTime.now();
            ingresoDAO.actualizarFechaSalida(Integer.parseInt(idRegistro), fechaSalida);
            response.sendRedirect("registro_vehiculos");
        } catch (SQLException e) {
            ErrorLog(response, "Error al actualizar la fecha de salida en la base de datos: " + e.getMessage());
        } catch (NamingException e) {
            ErrorLog(response, "Error de nombramiento al acceder a la base de datos: " + e.getMessage());
        } catch (Exception e) {
            ErrorLog(response, "Error inesperado: " + e.getMessage());
        } finally {
            if (ingresoDAO != null) {
                try {
                    ingresoDAO.close();
                } catch (SQLException e) {
                    ErrorLog.log("Error al cerrar Ingreso_VehiculosDAO: " + e.getMessage(), ErrorLog.Level.ERROR);
                }
            }
        }
    }

    private void ErrorLog(HttpServletResponse response, String message) throws IOException {
        ErrorLog.log(message, ErrorLog.Level.ERROR);
        response.sendRedirect("error.jsp");
    }
}
