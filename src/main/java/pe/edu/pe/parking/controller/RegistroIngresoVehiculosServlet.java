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
import java.time.ZoneId;
import java.time.ZonedDateTime;

@WebServlet(name = "ingreso_vehiculos", urlPatterns = {"/ingreso_vehiculos"})
public class RegistroIngresoVehiculosServlet extends HttpServlet {
    private Ingreso_VehiculosDAO ingresoVehiculosDAO;

    @Override
    public void init() throws ServletException {
        try {
            ingresoVehiculosDAO = new Ingreso_VehiculosDAO();
        } catch (SQLException | NamingException e) {
            throw new ServletException("No se pudo inicializar el DAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar si el usuario ha iniciado sesión
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Verificar si el usuario tiene permiso para acceder a la página
        if (!usuario.puedeAccederAPagina("ingreso_vehiculos")) {
            response.sendRedirect(request.getContextPath() + "/conductor");
            return;
        }

        String numeroPlaca = request.getParameter("numeroPlaca");
        String observacion = request.getParameter("observacion");
        int piso = Integer.parseInt(request.getParameter("piso"));
        int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
        int idTarifa = Integer.parseInt(request.getParameter("id_tarifa"));

        try {
            ZonedDateTime fechaHoraIngreso = ZonedDateTime.now(ZoneId.of("America/Lima"));

            Registro_Vehiculos nuevoRegistro = new Registro_Vehiculos.Builder(numeroPlaca, 1, idUsuario, idTarifa, piso, fechaHoraIngreso.toLocalDateTime())
                    .withObservacion(observacion != null && !observacion.isEmpty() ? observacion : "Sin observaciones")
                    .withEstado("En Estacionamiento")
                    .build();

            ingresoVehiculosDAO.registrarIngreso(nuevoRegistro, idUsuario);
            response.sendRedirect(request.getContextPath() + "/registro_vehiculos");
        } catch (IllegalArgumentException e) {
            ErrorLog.log("Error de validación: " + e.getMessage(), ErrorLog.Level.ERROR);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("registro_vehiculos").forward(request, response);
        } catch (SQLException e) {
            ErrorLog.log("Error al registrar el ingreso del vehículo: " + e.getMessage(), ErrorLog.Level.ERROR);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al registrar el vehículo");
        }
    }

    @Override
    public void destroy() {
        // Cerrar recursos y conexiones
        if (ingresoVehiculosDAO != null) {
            try {
                ingresoVehiculosDAO.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar Ingreso_VehiculosDAO: " + e.getMessage());
            }
        }
        super.destroy();
    }
}
