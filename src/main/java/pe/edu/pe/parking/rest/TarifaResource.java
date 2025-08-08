package pe.edu.pe.parking.rest;

import pe.edu.pe.parking.business.TarifaDAO;
import pe.edu.pe.parking.model.Tarifa;
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

@Path("/tarifas")
public class TarifaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tarifa> getTarifa() throws SQLException, NamingException {
        TarifaDAO tarifa = new TarifaDAO();
        List<Tarifa> lista = tarifa.gettAllTarifa();
        tarifa.close();
        return lista;
    }

    @GET
    @Path("/precio/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public double getTarifaPorPrecio(@PathParam("id") String idTarifa) throws SQLException, NamingException {
        TarifaDAO tarifaDAO = new TarifaDAO();
        int tarifaId = Integer.parseInt(idTarifa);
        double tarifa = tarifaDAO.getPrecioPorMinuto(tarifaId);
        tarifaDAO.close();
        return tarifa;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tarifa getTarifaPorId(@PathParam("id") String idTarifa) throws SQLException, NamingException {
        TarifaDAO tarifaDAO = new TarifaDAO();
        int tarifaId = Integer.parseInt(idTarifa);
        Tarifa tarifa = tarifaDAO.GetByIdTarifa(tarifaId);
        tarifaDAO.close();
        return tarifa;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> actualizarTarifa(@QueryParam("id") String idTarifa,
                               @QueryParam("nuevo_precio") String nuevoPrecio) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        TarifaDAO tarifaDAO = new TarifaDAO();
        try {
            // Validamos que todos los parámetros estén presentes
            if (idTarifa == null || nuevoPrecio == null) {
                resultado.put("error", "Faltan parámetros necesarios para actualizar el vehículo.");
                return resultado;
            }

            // Convertimos los parámetros a los tipos adecuados
            int tarifaId;
            double precioNuevo;
            tarifaId = Integer.parseInt(idTarifa);
            precioNuevo = Double.parseDouble(nuevoPrecio);
            tarifaDAO.updateTarifa(tarifaId, precioNuevo);
            tarifaDAO.close();

            resultado.put("success", "Tarifa actualizada con éxito: ID " + tarifaId);
        } catch (Exception e) {
            ErrorLog.log("Error al actualizar tarifa: " + e.getMessage(), ErrorLog.Level.WARN);
            resultado.put("error", "No se encontró una tarifa con el ID: " + e.getMessage());
        }
        return resultado;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> registrarTarifa(@Context UriInfo ui) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        MultivaluedMap<String, String> param = ui.getQueryParameters();

        try {
            // Obtener datos
            String tipoVehiculo = param.getFirst("tipo_vehiculo").toString();
            String precioPorMinutoS = param.getFirst("precio_por_minuto").toString();

            // Convertir datos
            Tarifa.TipoVehiculo tipoVehiculoEnum = null;
            for (Tarifa.TipoVehiculo tv : Tarifa.TipoVehiculo.values()) {
                if (tv.name().equalsIgnoreCase(tipoVehiculo)) {
                    tipoVehiculoEnum = tv;
                    break;
                }
            }
            double precioPorMinuto = Double.parseDouble(precioPorMinutoS);

            // Crear la tarifa
            Tarifa tarifa = new Tarifa(tipoVehiculoEnum, precioPorMinuto);

            // Registrar tarifa
            TarifaDAO tarifaDAO = new TarifaDAO();
            tarifaDAO.registrarTarifa(tarifa);
            tarifaDAO.close();

            resultado.put("success", "Tarifa registrada con éxito para el tipo de vehículo: " + tipoVehiculo);
        } catch (Exception e) {
            ErrorLog.log("Error al registrar tarifa: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al registrar la tarifa: " + e.getMessage());
        }
        return resultado;
    }

    @DELETE
    @Path("/{tipoVehiculo}")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> eliminarTarifaPorTipo(@PathParam("tipoVehiculo") String tipoVehiculo) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        TarifaDAO tarifaDAO = new TarifaDAO();

        try {
            // Convertir el String a un valor del enum TipoVehiculo de forma insensible al caso
            Tarifa.TipoVehiculo tipoVehiculoEnum = null;
            for (Tarifa.TipoVehiculo tv : Tarifa.TipoVehiculo.values()) {
                if (tv.name().equalsIgnoreCase(tipoVehiculo)) {
                    tipoVehiculoEnum = tv;
                    break;
                }
            }

            // Si no se encontró un tipo válido se lanza una excepción
            if (tipoVehiculoEnum == null) {
                throw new IllegalArgumentException("El tipo de vehículo especificado no es válido: " + tipoVehiculo);
            }

            // Llamar al DAO para eliminar la tarifa
            tarifaDAO.eliminarTarifaPorTipoVehiculo(tipoVehiculoEnum);

            resultado.put("success", "Tarifa eliminada con éxito para el tipo de vehículo: " + tipoVehiculoEnum);
        } catch (Exception e) {
            ErrorLog.log("Error al eliminar tarifa: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al eliminar la tarifa: " + e.getMessage());
        }
        return resultado;
    }
}
