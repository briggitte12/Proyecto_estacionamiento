package pe.edu.pe.parking.rest;

import pe.edu.pe.parking.business.ConductorDAO;
import pe.edu.pe.parking.model.Conductor;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Path("/conductores")
public class ConductorResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Conductor> getConductor() throws SQLException, NamingException {
        ConductorDAO conductor = new ConductorDAO();
        List<Conductor> lista = conductor.gettAllConductores();
        conductor.close();
        return lista;
    }

    @GET
    @Path("/{dni}")
    @Produces(MediaType.APPLICATION_JSON)
    public Conductor getConductorPorDNI(@PathParam("dni") String dni) throws SQLException, NamingException, IOException {
        ConductorDAO conductorDAO = new ConductorDAO();
        Conductor conductor = conductorDAO.getConductorByDni(dni);
        conductorDAO.close();
        return conductor;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> registrarConductor(@Context UriInfo ui) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        MultivaluedMap<String, String> param = ui.getQueryParameters();

        try {
            // Obtener parámetros desde la URL
            String dniConductor = param.getFirst("dni_conductor").toString();
            String nombreCompleto = param.getFirst("nombre_completo").toString();
            String celular = param.getFirst("celular").toString();
            String correo = param.getFirst("correo").toString();
            String strFechaNacimiento = param.getFirst("fecha_nacimiento").toString();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(strFechaNacimiento, dtf);
            String direccion = param.getFirst("direccion").toString();
            String genero = param.getFirst("genero").toString();

            // Validación de parámetros
            if (dniConductor == null || nombreCompleto == null || celular == null || correo == null || strFechaNacimiento == null || direccion == null || genero == null) {
                resultado.put("error", "Faltan parámetros necesarios para registrar el conductor.");
                return resultado;
            }

            // Crear objeto Conductor
            Conductor conductor = new Conductor(dniConductor, nombreCompleto, celular, correo, fecha, direccion, Conductor.Genero.valueOf(genero));

            // Registrar conductor en la base de datos
            ConductorDAO conductorDAO = new ConductorDAO();
            conductorDAO.RegistrarConductor(conductor);
            resultado.put("success", "Conductor registrado con éxito: " + nombreCompleto);
        } catch (Exception e) {
            ErrorLog.log("Error al registrar vehículo: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al registrar el vehículo: " + e.getMessage());
        }
        return resultado;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> actualizarConductor(@Context UriInfo ui) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        MultivaluedMap<String, String> param = ui.getQueryParameters();

        try {
            // Obtener datos
            String dniConductor = param.getFirst("dni_conductor").toString();
            String nombreCompleto = param.getFirst("nombre_completo").toString();
            String celular = param.getFirst("celular").toString();
            String correo = param.getFirst("correo").toString();
            String strFechaNacimiento = param.getFirst("fecha_nacimiento").toString();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(strFechaNacimiento, dtf);
            String genero = param.getFirst("genero").toString();

            // Validación de parámetros
            if (dniConductor == null || nombreCompleto == null || celular == null || correo == null || strFechaNacimiento == null || genero == null) {
                resultado.put("error", "Faltan parámetros necesarios para registrar el conductor.");
                return resultado;
            }

            // Crear objeto Conductor sin dirección
            Conductor conductor = new Conductor.Builder(dniConductor, nombreCompleto, celular, correo, fecha, Conductor.Genero.valueOf(genero)).build();

            ConductorDAO conductorDAO = new ConductorDAO();
            conductorDAO.updateConductor(conductor);
            resultado.put("success", "Conductor actualizado con éxito: " + nombreCompleto);
        } catch (Exception e) {
            ErrorLog.log("Error al actualizar conductor: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al actualizar el conductor: " + e.getMessage());
        }
        return resultado;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> eliminarConductor(@QueryParam("dni") String dni) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        ConductorDAO conductorDAO = new ConductorDAO();
        try {
            // Validar que el DNI no esté vacío
            if (dni == null || dni.isEmpty()) {
                resultado.put("error", "El DNI del conductor es requerido.");
                return resultado;
            }

            // Eliminar el conductor
            conductorDAO.eliminarConductor(dni);
            conductorDAO.close();
            resultado.put("success", "El conductor con DNI " + dni + " fue eliminado con éxito.");
        } catch (Exception e) {
            ErrorLog.log("Error al eliminar conductor: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al eliminar conductor: " + e.getMessage());
        }
        return resultado;
    }
}