package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.VehiculoDAO;
import pe.edu.pe.parking.model.Usuario;
import pe.edu.pe.parking.model.Vehiculo;
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
import java.util.List;

@WebServlet(name = "vehiculo", urlPatterns = {"/vehiculo"})
public class VehiculoListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
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
            if (!usuario.puedeAccederAPagina("vehiculo")) {
                resp.sendRedirect(req.getContextPath() + "/conductor");
                return;
            }

            VehiculoDAO dao = new VehiculoDAO();
            List<Vehiculo> allVehiculos = dao.getAllVehiculo();
            dao.close();

            ErrorLog.log("Se muestra a los vehiculos", ErrorLog.Level.INFO);

            req.setAttribute("lista_vehiculos", allVehiculos);
            req.getRequestDispatcher("vehiculo.jsp").forward(req, resp);

        } catch (SQLException | NamingException e) {
            ErrorLog.log("Error en VehiculoServlet: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new ServletException(e);
        }
    }
}

