package pe.edu.pe.parking.controller;

import pe.edu.pe.parking.business.TarifaDAO;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "EnviarDatos", urlPatterns = {"/EnviarDatos"})
public class EnviarDatosPagoServlet extends HttpServlet {

    private TarifaDAO tarifaDAO;

    @Override
    public void init() throws ServletException {
        try {
            // Inicializamos el DAO de tarifas
            tarifaDAO = new TarifaDAO();
        } catch (SQLException | NamingException e) {
            try {
                ErrorLog.log("No se pudo inicializar el DAO de tarifas: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ex) {
                throw new ServletException("Error al registrar ", ex);
            }
            throw new ServletException("No se pudo inicializar el DAO de tarifas", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idRegistroStr = request.getParameter("id_registro");
        String fechaIngresoStr = request.getParameter("fechaIngreso");
        String fechaSalidaStr = request.getParameter("fechaSalida");
        String idTarifaStr = request.getParameter("id_tarifa");
        long minutosEstacionado = 0;
        double montoTotal = 0.0;
        double precioPorMinuto = 0.0;

        try {
            // Verificar y calcular minutos estacionados
            if (fechaIngresoStr != null && fechaSalidaStr != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime fechaIngreso = LocalDateTime.parse(fechaIngresoStr, formatter);
                LocalDateTime fechaSalida = LocalDateTime.parse(fechaSalidaStr, formatter);
                Duration duration = Duration.between(fechaIngreso, fechaSalida);
                minutosEstacionado = duration.toMinutes();
            }

            // Validar y calcular el monto total
            if (minutosEstacionado > 0 && idTarifaStr != null) {
                int idTarifa = Integer.parseInt(idTarifaStr);
                precioPorMinuto = tarifaDAO.getPrecioPorMinuto(idTarifa);
                if (precioPorMinuto > 0) {
                    montoTotal = minutosEstacionado * precioPorMinuto;
                } else {
                    ErrorLog.log("Precio por minuto no válido para tarifa: " + idTarifa, ErrorLog.Level.WARN);
                    request.setAttribute("error", "Precio por minuto no válido.");
                }
            } else {
                ErrorLog.log("Tarifa o fechas incorrectas", ErrorLog.Level.WARN);
                request.setAttribute("error", "Debe seleccionar una tarifa válida y fechas correctas.");
            }

        } catch (Exception e) {
            ErrorLog.log("Error en el proceso de registro de pago: " + e.getMessage(), ErrorLog.Level.ERROR);
            request.setAttribute("error", "Ocurrió un error al calcular el pago.");
        }

        // Enviar resultados al JSP
        request.setAttribute("id_registro", idRegistroStr);
        request.setAttribute("id_tarifa", idTarifaStr);
        request.setAttribute("minutosEstacionado", minutosEstacionado);
        request.setAttribute("montoTotal", montoTotal);

        // Redirigir al JSP
        request.getRequestDispatcher("add_pago.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // Cerrar recursos y conexiones
        if (tarifaDAO != null) {
            try {
                tarifaDAO.close();
            } catch (SQLException e) {
                try {
                    ErrorLog.log("Error al cerrar TarifaDAO: " + e.getMessage(), ErrorLog.Level.ERROR);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        super.destroy();
    }
}

