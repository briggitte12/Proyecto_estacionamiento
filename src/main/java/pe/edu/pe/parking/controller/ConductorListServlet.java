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
import java.util.List;

@WebServlet(name = "conductor", urlPatterns = {"/conductor"})
public class ConductorListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Conductor> allConductores = new ConductorDAO().gettAllConductores();

            ErrorLog.log("Se muestra a los conductores", ErrorLog.Level.INFO);

            req.setAttribute("lista_conductores", allConductores);
            req.getRequestDispatcher("conductor.jsp").forward(req, resp);

        } catch (SQLException | NamingException e) {
            ErrorLog.log("Error en ConductorListServlet: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new ServletException(e);
        }
    }


}
