package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.*;
import pe.edu.pe.parking.model.Tarifa;
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
import java.util.List;

@WebServlet(name = "dashboard", urlPatterns = {"/dashboard"})
public class DashboardListServer extends HttpServlet {

    private TarifaDAO tarifaDAO;
    private UsuarioDAO usuarioDAO;
    private VehiculoDAO vehiculoDAO;
    private ConductorDAO conductorDAO;
    private CobroDAO cobroDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            tarifaDAO = new TarifaDAO();
            usuarioDAO = new UsuarioDAO();
            vehiculoDAO = new VehiculoDAO();
            conductorDAO = new ConductorDAO();
            cobroDAO = new CobroDAO();
        } catch (SQLException | NamingException e) {
            throw new ServletException("Error al inicializar los DAOs: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar si el usuario ha iniciado sesión
        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // Verificar si el usuario tiene permiso para acceder a la página
        if (!usuario.puedeAccederAPagina("dashboard")) {
            resp.sendRedirect(req.getContextPath() + "/conductor");
            return;
        }

        try {
            List<Tarifa> allTarifa = tarifaDAO.gettAllTarifa();
            int allUsuarios = usuarioDAO.getCantidadUsuarios();
            int allVehiculos = vehiculoDAO.getCantidadVehiculos();
            int allConductores = conductorDAO.getCantidadConductores();
            double totalGanancias = cobroDAO.getTotalGanancia();

            req.setAttribute("lista_tarifa", allTarifa);
            req.setAttribute("cantidad_vehiculos", allVehiculos);
            req.setAttribute("cantidad", allConductores);
            req.setAttribute("total_ganancias", totalGanancias);
            req.setAttribute("cantidad_usuario", allUsuarios);
            req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
        } catch (SQLException | NamingException e) {
            throw new ServletException("Error en DashboardListServer: " + e.getMessage(), e);
        }
    }


    @Override
    public void destroy() {
        try {
            if (tarifaDAO != null) tarifaDAO.close();
            if (usuarioDAO != null) usuarioDAO.close();
            if (vehiculoDAO != null) vehiculoDAO.close();
            if (conductorDAO != null) conductorDAO.close();
            if (cobroDAO != null) cobroDAO.close();
        } catch (SQLException e) {

        }
        super.destroy();
    }
}
