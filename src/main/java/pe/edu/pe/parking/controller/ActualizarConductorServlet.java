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
import java.time.LocalDate;

@WebServlet(name = "ActualizarConductor", urlPatterns = {"/ActualizarConductor"})
public class ActualizarConductorServlet extends HttpServlet {

    private ConductorDAO conductorDAO;

    @Override
    public void init() throws ServletException {
        try {
            conductorDAO = new ConductorDAO();
        } catch (SQLException | NamingException e) {
            throw new ServletException("Error inicializando ConductorDAO: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dniConductor = request.getParameter("dni_conductor");
        String nombreCompleto = request.getParameter("nombre_completo");
        String celular = request.getParameter("celular");
        String correo = request.getParameter("correo");
        String fechaNacimiento = request.getParameter("fecha_nacimiento");
        String direccion = request.getParameter("direccion");
        String genero = request.getParameter("genero");

        if (dniConductor != null && nombreCompleto != null) {
            try {
                Conductor conductor = new Conductor.Builder(dniConductor, nombreCompleto, celular, correo, LocalDate.parse(fechaNacimiento), Conductor.Genero.valueOf(genero))
                        .withdireccion(direccion)
                        .build();

                if (conductorDAO.updateConductor(conductor)) {
                    response.sendRedirect(request.getContextPath() + "/conductor");
                } else {
                    LogERROR("No se pudo actualizar el conductor con DNI: " + dniConductor, request, response);
                }
            } catch (IllegalArgumentException e) {
                LogERROR("Datos inválidos o error en la actualización: " + e.getMessage(), request, response);
            }
        } else {
            LogERROR("Datos requeridos faltantes para la actualización del conductor", request, response);
        }
    }

    private void LogERROR(String message, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ErrorLog.log(message, ErrorLog.Level.ERROR);
        request.setAttribute("error", message);
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        try {
            conductorDAO.close();
        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al cerrar ConductorDAO: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
