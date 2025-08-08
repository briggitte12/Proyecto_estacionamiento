package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.model.Usuario;
import pe.edu.pe.parking.util.ErrorLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "usuario", urlPatterns = {"/usuario"})
public class UsuarioListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);

            // Verificar si hay sesión y si el usuario ha iniciado sesión
            if (session == null || session.getAttribute("usuario") == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }

            Usuario usuario = (Usuario) session.getAttribute("usuario");

            // Verificar si el usuario tiene permiso para acceder a esta página
            if (!usuario.puedeAccederAPagina("usuario")) {
                resp.sendRedirect(req.getContextPath() + "/usuario");
                return;
            }

            ErrorLog.log("Datos de usuario recuperados correctamente", ErrorLog.Level.INFO);

            req.setAttribute("usuario", usuario);
            req.getRequestDispatcher("profile.jsp").forward(req, resp);

        } catch (Exception e) {
            ErrorLog.log("Error al procesar el servlet: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new ServletException(e);
        }
    }
}

