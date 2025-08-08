package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.Ingreso_VehiculosDAO;
import pe.edu.pe.parking.model.Registro_Vehiculos;
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
import java.util.List;

@WebServlet(name = "registro_vehiculos", urlPatterns = {"/registro_vehiculos"})
public class IngresoVehiculosListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);

            // Verificar si hay sesión
            if (session == null || session.getAttribute("usuario") == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }

            Usuario usuario = (Usuario) session.getAttribute("usuario");

            // Verificar si el usuario tiene permiso para acceder a esta página
            if (!usuario.puedeAccederAPagina("registro_vehiculos")) {
                resp.sendRedirect(req.getContextPath() + "/conductor");
                return;
            }

            Ingreso_VehiculosDAO dao = new Ingreso_VehiculosDAO();
            List<Registro_Vehiculos> allRegistro = dao.getAllRegistroVehiculo();
            dao.close();

            ErrorLog.log("Se muestra el detalle del ingreso de vehículos", ErrorLog.Level.INFO);

            req.setAttribute("lista_registro", allRegistro);
            req.getRequestDispatcher("ingreso_vehiculo.jsp").forward(req, resp);

        } catch (SQLException | NamingException e) {
            ErrorLog.log("Error en IngresoVehiculosListServlet: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new ServletException(e);
        }
    }
}
