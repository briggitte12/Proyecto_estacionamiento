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

@WebServlet(name = "RegistrarCobro", urlPatterns = {"/RegistrarCobro"})
public class RegistrarCobroServlet extends HttpServlet {

    private CobroDAO cobroDAO;

    @Override
    public void init() throws ServletException {
        try {
            // Inicializamos el DAO de cobros
            cobroDAO = new CobroDAO();
        } catch (SQLException | NamingException e) {
            try {
                ErrorLog.log("No se pudo inicializar el DAO de cobros: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ex) {
                throw new ServletException("Error al registrar en el log", ex);
            }
            throw new ServletException("No se pudo inicializar el DAO de cobros", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idRegistroStr = request.getParameter("id_registro");
        String idTarifaStr = request.getParameter("id_tarifa");
        int minutosEstacionado = Integer.parseInt(request.getParameter("minutosEstacionado"));

        // Eliminar el símbolo de moneda "S/." antes de convertirlo a double
        String montoTotalStr = request.getParameter("montoTotal").replace("S/.", "").trim();
        double montoTotal;

        try {
            montoTotal = Double.parseDouble(montoTotalStr);
        } catch (NumberFormatException e) {
            ErrorLog.log("Formato de monto inválido: " + montoTotalStr, ErrorLog.Level.ERROR);
            request.setAttribute("error", "El formato del monto no es válido.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            // Registrar el cobro en la base de datos
            Cobro cobro = new Cobro(Integer.parseInt(idRegistroStr), Integer.parseInt(idTarifaStr), minutosEstacionado, montoTotal);
            boolean registrado = cobroDAO.registrarCobro(cobro);

            if (registrado) {
                response.sendRedirect(request.getContextPath() + "/detalle");
            } else {
                ErrorLog.log("No se pudo registrar el cobro para el registro ID: " + idRegistroStr, ErrorLog.Level.WARN);
                request.setAttribute("error", "No se pudo registrar el cobro.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            ErrorLog.log("Error al registrar el cobro: " + e.getMessage(), ErrorLog.Level.ERROR);
            request.setAttribute("error", "Ocurrió un error al registrar el cobro.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        // Cerrar recursos y conexiones
        if (cobroDAO != null) {
            try {
                cobroDAO.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar CobroDAO: " + e.getMessage());
            }
        }
        super.destroy();
    }
}
