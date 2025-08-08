package pe.edu.pe.parking.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "logout", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Invalidar la sesión
            session.invalidate();
        }

        // Redirigir a la página de inicio de sesión
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
