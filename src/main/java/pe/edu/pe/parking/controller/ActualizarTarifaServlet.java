package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.TarifaDAO;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ActualizarTarifa", urlPatterns = {"/ActualizarTarifa"})
public class ActualizarTarifaServlet extends HttpServlet {

    private TarifaDAO tarifaDAO;

    @Override
    public void init() throws ServletException {
        try {
            tarifaDAO = new TarifaDAO();
        } catch (SQLException | NamingException e) {
            throw new ServletException("Error inicializando TarifaDAO", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idTarifaStr = request.getParameter("id_tarifa");
        String precioPorMinutoStr = request.getParameter("precio_por_minuto");

        if (idTarifaStr != null && precioPorMinutoStr != null) {
            try {
                int idTarifa = Integer.parseInt(idTarifaStr);
                double nuevoPrecio = Double.parseDouble(precioPorMinutoStr);

                // Actualizar la tarifa
                boolean actualizacionExitosa = tarifaDAO.updateTarifa(idTarifa, nuevoPrecio);

                if (actualizacionExitosa) {
                    response.sendRedirect(request.getContextPath() + "/dashboard");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo actualizar la tarifa.");
                }
            } catch (NumberFormatException | SQLException e) {
                ErrorLog.log("Error al actualizar tarifa: " + e.getMessage(), ErrorLog.Level.ERROR);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos inválidos o error en la actualización.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros faltantes.");
        }
    }

    @Override
    public void destroy() {
        try {
            tarifaDAO.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

