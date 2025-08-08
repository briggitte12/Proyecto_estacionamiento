package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.UsuarioDAO;
import pe.edu.pe.parking.model.Usuario;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usuario = req.getParameter("txtusuario");
        String clave = req.getParameter("txtPassword");

        try {
            // Validar si el usuario es válido
            if (UsuarioDAO.isValidUser(usuario, clave)) {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario usuarioValido = usuarioDAO.getUsuario(usuario);

                // Crear una sesión y almacenar el objeto Usuario
                HttpSession session = req.getSession();
                session.setAttribute("usuario", usuarioValido);

                // Verificar si el usuario tiene permiso para acceder al dashboard
                if (usuarioValido.puedeAccederAPagina("dashboard")) {
                    resp.sendRedirect(req.getContextPath() + "/dashboard");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/conductor");
                }
            } else {
                resp.getWriter().println("<html><body><h3>Usuario o contraseña incorrectos. Intente de nuevo.</h3><a href='login.jsp'>Volver a intentar</a></body></html>");
            }
        } catch (SQLException | NamingException e) {
            throw new ServletException(e);
        }
    }
}
