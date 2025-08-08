package pe.edu.pe.parking.rest;

import pe.edu.pe.parking.business.Ingreso_VehiculosDAO;
import pe.edu.pe.parking.model.Registro_Vehiculos;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Path("/ingresos")
public class IngresoVehiculosResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Registro_Vehiculos> getIngresoVehiculos() throws SQLException, NamingException, IOException {
        Ingreso_VehiculosDAO ingreso = new Ingreso_VehiculosDAO();
        List<Registro_Vehiculos> lista = ingreso.getAllRegistroVehiculo();
        ingreso.close();
        return lista;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> registrarIngreso(@Context UriInfo ui) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        MultivaluedMap<String, String> param = ui.getQueryParameters();

        try {
            // Obtener datos
            String numeroPlaca = param.getFirst("numero_placa").toString();
            String observacion = param.getFirst("observacion").toString();
            int piso = Integer.parseInt(param.getFirst("piso").toString());
            int idUsuario = Integer.parseInt(param.getFirst("id_usuario").toString());
            int idTarifa = Integer.parseInt(param.getFirst("id_tarifa").toString());

            ZonedDateTime fechaHoraIngreso = ZonedDateTime.now(ZoneId.of("America/Lima"));
            Registro_Vehiculos nuevoRegistro = new Registro_Vehiculos.Builder(numeroPlaca, 1, idUsuario, idTarifa, piso, fechaHoraIngreso.toLocalDateTime())
                    .withObservacion(observacion != null && !observacion.isEmpty() ? observacion : "Sin observaciones")
                    .withEstado("En Estacionamiento")
                    .build();

            Ingreso_VehiculosDAO ingresoVehiculosDAO = new Ingreso_VehiculosDAO();
            ingresoVehiculosDAO.registrarIngreso(nuevoRegistro, idUsuario);
            ingresoVehiculosDAO.close();

            resultado.put("success", "Ingreso de vehículo registrado con éxito.");
        } catch (Exception e) {
            ErrorLog.log("Error al registrar ingreso de vehículo: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al registrar ingreso de vehículo: " + e.getMessage());
        }
        return resultado;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> actualizarIngreso(@QueryParam("id_registro") String idRegistro,
                                                   @QueryParam("fecha_salida") String fechaSalida) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        Ingreso_VehiculosDAO ingresoVehiculosDAO = new Ingreso_VehiculosDAO();

        try {
            // Validar que los parámetros no sean nulos
            if (idRegistro == null || fechaSalida == null) {
                resultado.put("error", "Faltan parámetros necesarios para actualizar el ingreso de vehículo.");
                return resultado;
            }

            // Convertir los parámetros
            int id = Integer.parseInt(idRegistro);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fecha = LocalDateTime.parse(fechaSalida, dtf);

            // Actualizar la fecha de salida del vehículo
            ingresoVehiculosDAO.actualizarFechaSalida(id, fecha);
            ingresoVehiculosDAO.close();

            resultado.put("success", "Fecha de salida actualizada con éxito.");
        } catch (Exception e) {
            ErrorLog.log("Error al actualizar fecha de salida: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al actualizar fecha de salida: " + e.getMessage());
        }
        return resultado;
    }

    @PUT @Path("/salida")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> actualizarIngresoVehiculos(@QueryParam("id_registro") String idRegistro) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        Ingreso_VehiculosDAO ingresoVehiculosDAO = new Ingreso_VehiculosDAO();

        try {
            // Get data
            int id = Integer.parseInt(idRegistro);

            // Actualizar la fecha de salida del vehículo
            LocalDateTime fechaSalida = LocalDateTime.now();
            ingresoVehiculosDAO.actualizarFechaSalida(id, fechaSalida);
            ingresoVehiculosDAO.close();

            resultado.put("success", "Fecha de salida actualizada con éxito.");
        } catch (Exception e) {
            ErrorLog.log("Error al actualizar fecha de salida con el ID de registro: " + idRegistro, ErrorLog.Level.ERROR);
            resultado.put("error", "Error al actualizar fecha de salida: " + e.getMessage());
        }
        return resultado;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> eliminarIngreso(@PathParam("id") String id) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        Ingreso_VehiculosDAO ingresoVehiculosDAO = new Ingreso_VehiculosDAO();
        try {
            // Validar que el ID no esté vacío
            if (id == null || id.isEmpty()) {
                resultado.put("error", "El ID de registro es requerido.");
                return resultado;
            }

            // Convertimos el ID
            int idRegistro = Integer.parseInt(id);

            // Eliminar el ingreso
            ingresoVehiculosDAO.eliminarIngreso(idRegistro);
            ingresoVehiculosDAO.close();
            resultado.put("success", "El ingreso del vehículo con ID " + id + " fue eliminado con éxito.");
        } catch (Exception e) {
            ErrorLog.log("Error al eliminar ingreso de vehículo: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al eliminar ingreso de vehículo: " + e.getMessage());
        }
        return resultado;
    }
}