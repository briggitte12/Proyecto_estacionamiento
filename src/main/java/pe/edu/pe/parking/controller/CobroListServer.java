package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.CobroDAO;
import pe.edu.pe.parking.model.Cobro;
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

@WebServlet(name = "detalle", urlPatterns = {"/detalle"})
public class CobroListServer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Cobro> allCobro = new CobroDAO().getAllCobro();

            ErrorLog.log("Se muestra el detalle de los cobros", ErrorLog.Level.INFO);

            req.setAttribute("lista_cobro", allCobro);
            req.getRequestDispatcher("detalle.jsp").forward(req, resp);

        } catch (SQLException | NamingException e) {
            ErrorLog.log("Error en CobroListServlet: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new ServletException(e);
        }
    }


}
