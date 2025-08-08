package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.ConductorDAO;
import pe.edu.pe.parking.model.Conductor;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ObtenerConductor", urlPatterns = {"/ObtenerConductor"})
public class ObtenerConductorServlet extends HttpServlet {

    private ConductorDAO conductorDAO;

    @Override
    public void init() throws ServletException {
        try {
            conductorDAO = new ConductorDAO();
        } catch (SQLException | NamingException e) {
            throw new ServletException("Error inicializando ConductorDAO: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dniConductor = request.getParameter("dniConductor");

        if (dniConductor != null) {
            try {
                Conductor conductor = conductorDAO.getConductorByDni(dniConductor);
                if (conductor != null) {
                    request.setAttribute("conductor", conductor);
                    request.getRequestDispatcher("editarConductor.jsp").forward(request, response);
                } else {
                    ErrorLOG("Conductor no encontrado para el DNI: " + dniConductor, request, response);
                }
            } catch (Exception e) {
                ErrorLOG("Error al obtener el conductor: " + e.getMessage(), request, response);
            }
        } else {
            ErrorLOG("DNI de conductor es requerido pero no fue proporcionado", request, response);
        }
    }

    private void ErrorLOG(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ErrorLog.log(message, ErrorLog.Level.ERROR);
        request.setAttribute("error", message);
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        try {
            conductorDAO.close();
        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al cerrar ConductorDAO: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
