package pe.edu.pe.parking.rest;

import pe.edu.pe.parking.business.CobroDAO;
import pe.edu.pe.parking.model.Cobro;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Path("/cobros")
public class CobroResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cobro> getCobro() throws SQLException, NamingException {
        CobroDAO cobro = new CobroDAO();
        List<Cobro> lista = cobro.getAllCobro();
        cobro.close();
        return lista;
    }

    @GET
    @Path("/ganancia")
    @Produces(MediaType.APPLICATION_JSON)
    public double getTotalGanancias() throws SQLException, NamingException, IOException {
        CobroDAO cobro = new CobroDAO();
        double ganancia = cobro.getTotalGanancia();
        cobro.close();
        return ganancia;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> registrarCobro(@Context UriInfo ui) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        MultivaluedMap<String, String> params = ui.getQueryParameters();
        CobroDAO cobroDAO = new CobroDAO();

        try {
            // Convertir los parámetros a los tipos esperados
            int idRegistro = Integer.parseInt(params.getFirst("id_registro"));
            int idTarifa = Integer.parseInt(params.getFirst("id_tarifa"));
            int minutosEstacionado = Integer.parseInt(params.getFirst("minutos_estacionado"));
            double montoTotal = Double.parseDouble(params.getFirst("monto_total"));

            // Crear un objeto Cobro
            Cobro nuevoCobro = new Cobro(idRegistro, idTarifa, minutosEstacionado, montoTotal);

            // Registrar el cobro en la DB
            cobroDAO.registrarCobro(nuevoCobro);
            cobroDAO.close();
            resultado.put("success", "Cobro registrado con éxito.");
        } catch (Exception e) {
            ErrorLog.log("Error al registrar cobro: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al registrar cobro: " + e.getMessage());
        }
        return resultado;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> actualizarCobro(@QueryParam("id") String idCobro,
                                                   @QueryParam("minutos") String minutosEstacionados,
                                                   @QueryParam("monto") String montoTotal) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        CobroDAO cobroDAO = new CobroDAO();

        try {
            // Validar que los parámetros no sean nulos
            if (idCobro == null || minutosEstacionados == null || montoTotal == null) {
                resultado.put("error", "Faltan parámetros necesarios para actualizar el cobro.");
                return resultado;
            }

            // Convertir los parámetros
            int cobroId = Integer.parseInt(idCobro);
            int nuevosMinutos = Integer.parseInt(minutosEstacionados);
            double nuevoMonto = Double.parseDouble(montoTotal);

            cobroDAO.actualizarCobro(cobroId, nuevosMinutos, nuevoMonto);
            cobroDAO.close();

            resultado.put("success", "Cobro actualizado con éxito: ID " + cobroId);
        } catch (Exception e) {
            ErrorLog.log("Error al actualizar cobro: " + e.getMessage(), ErrorLog.Level.WARN);
            resultado.put("error", "Error al actualizar cobro: " + e.getMessage());
        }
        return resultado;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> eliminarCobro(@PathParam("id") String id) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        CobroDAO cobroDAO = new CobroDAO();

        try {
            // Validar que el parámetro no sea nulo
            if (id == null) {
                resultado.put("error", "Falta el parámetro 'id' para eliminar el cobro.");
                return resultado;
            }

            // Convertir el parámetro a tipo entero
            int cobroId = Integer.parseInt(id);

            // Llamar al DAO para eliminar
            cobroDAO.eliminarCobro(cobroId);
            cobroDAO.close();
            resultado.put("success", "Cobro eliminado con éxito para el ID: " + cobroId);
        } catch (Exception e) {
            ErrorLog.log("Error al eliminar cobro: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al eliminar el cobro: " + e.getMessage());
        }
        return resultado;
    }
}