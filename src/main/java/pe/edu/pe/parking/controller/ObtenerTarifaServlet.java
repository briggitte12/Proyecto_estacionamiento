package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.TarifaDAO;
import pe.edu.pe.parking.model.Tarifa;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ObtenerTarifa", urlPatterns = {"/ObtenerTarifa"})
public class ObtenerTarifaServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idTarifaStr = request.getParameter("id_tarifa");

        if (idTarifaStr != null) {
            try {
                int idTarifa = Integer.parseInt(idTarifaStr);
                // Obtener la tarifa por ID
                Tarifa tarifa = tarifaDAO.GetByIdTarifa(idTarifa);
                request.setAttribute("tarifa", tarifa);
                request.getRequestDispatcher("editarTarifa.jsp").forward(request, response);
            } catch (NumberFormatException | SQLException e) {
                ErrorLog.log("Error al obtener tarifa: " + e.getMessage(), ErrorLog.Level.ERROR);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al obtener los datos de la tarifa.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro id_tarifa es requerido.");
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
