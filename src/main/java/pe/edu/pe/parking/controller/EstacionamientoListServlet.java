package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.EstacionamientoDAO;
import pe.edu.pe.parking.model.Estacionamiento;
import pe.edu.pe.parking.model.Usuario;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "estacionamiento", urlPatterns = {"/estacionamiento"})
public class EstacionamientoListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);

            // Verificar si el usuario ha iniciado sesión
            if (session == null || session.getAttribute("usuario") == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }

            Usuario usuario = (Usuario) session.getAttribute("usuario");

            // Verificar si el usuario tiene permiso para acceder a esta página
            if (!usuario.puedeAccederAPagina("estacionamiento")) {
                resp.sendRedirect(req.getContextPath() + "/conductor");
                return;
            }

            EstacionamientoDAO dao = new EstacionamientoDAO();
            Estacionamiento estacionamiento = dao.getEstacionamiento();

            // Pasamos el objeto de estacionamiento como atributo
            req.setAttribute("estacionamiento", estacionamiento);
            req.getRequestDispatcher("estacionamiento.jsp").forward(req, resp);

            ErrorLog.log("Datos de estacionamiento recuperados correctamente", ErrorLog.Level.INFO);
        } catch (SQLException | NamingException e) {
            ErrorLog.log("Error al procesar el servlet: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new ServletException(e);
        }
    }
}
