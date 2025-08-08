package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.ConductorDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "enviarDatosConductor", urlPatterns = {"/enviarDatosConductor"})
public class EnviarDatosConductorExistente extends HttpServlet {

    private ConductorDAO conductorDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            conductorDAO = new ConductorDAO();
        } catch (Exception e) {
            throw new ServletException("Error al inicializar DAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dniConductor = req.getParameter("dniConductor");

        // Verificar si el conductor existe
        boolean existeConductor;
        try {
            existeConductor = conductorDAO.dniExistente(dniConductor);
        } catch (SQLException e) {
            req.setAttribute("mensaje", "Error al verificar DNI: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        if (existeConductor) {
            // Solo enviar el DNI a la JSP
            req.setAttribute("dniConductor", dniConductor);
            req.getRequestDispatcher("vehiculo_conductor_existente.jsp").forward(req, resp);
        } else {
            req.setAttribute("mensaje", "Conductor no encontrado.");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
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
        super.destroy();
    }
}
