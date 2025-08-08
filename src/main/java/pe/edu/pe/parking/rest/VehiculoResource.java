package pe.edu.pe.parking.rest;

import pe.edu.pe.parking.business.VehiculoDAO;
import pe.edu.pe.parking.model.Vehiculo;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Path("/vehiculos")
public class VehiculoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vehiculo> getVehiculo() throws SQLException, NamingException {
        VehiculoDAO vehiculo = new VehiculoDAO();
        List<Vehiculo> lista = vehiculo.getAllVehiculo();
        vehiculo.close();
        return lista;
    }

    @GET
    @Path("/{numeroPlaca}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vehiculo getVehiculoByPlaca(@PathParam("numeroPlaca") String numeroPlaca) throws SQLException, NamingException {
        VehiculoDAO vehiculoDAO = new VehiculoDAO();
        Vehiculo vehiculo = vehiculoDAO.obtenerVehiculo(numeroPlaca);
        vehiculoDAO.close();
        return vehiculo;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> registrarVehiculo(@Context UriInfo ui) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        MultivaluedMap<String, String> param = ui.getQueryParameters();

        try {
            // Get data
            String numeroPlaca = param.getFirst("numero_placa").toString();
            String tipoVehiculo = param.getFirst("tipo_vehiculo").toString();
            String marca = param.getFirst("marca").toString();
            String color = param.getFirst("color").toString();
            String dniConductor = param.getFirst("dni_conductor").toString();

            // Crear el objeto Vehiculo
            Vehiculo vehiculo = new Vehiculo.Builder(numeroPlaca, Vehiculo.Tipo_Vehiculo.valueOf(tipoVehiculo), dniConductor)
                    .withMarca(marca)
                    .withColor(color)
                    .build();

            VehiculoDAO vehiculoDAO = new VehiculoDAO();
            vehiculoDAO.RegistrarVehiculo(vehiculo);
            vehiculoDAO.close();
            resultado.put("success", "Vehículo registrado con éxito: " + numeroPlaca);
        } catch (Exception e) {
            ErrorLog.log("Error al registrar vehículo: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "Error al registrar el vehículo: " + e.getMessage());
        }
        return resultado;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> actualizarVehiculo(
            @QueryParam("numeroPlaca") String numeroPlaca,
            @QueryParam("tipoVehiculo") String tipoVehiculo,
            @QueryParam("marca") String marca,
            @QueryParam("color") String color,
            @QueryParam("dniConductor") String dniConductor) throws SQLException, NamingException, IOException {

        HashMap<String, String> resultado = new HashMap<>();
        VehiculoDAO vehiculoDAO = new VehiculoDAO();

        try {

            // Validar que todos los parámetros estén presentes
            if (numeroPlaca == null || tipoVehiculo == null || marca == null || color == null || dniConductor == null) {
                resultado.put("error", "Faltan parámetros necesarios para actualizar el vehículo.");
                return resultado;
            }

            // Crear el objeto Vehiculo
            Vehiculo vehiculo = new Vehiculo.Builder(numeroPlaca, Vehiculo.Tipo_Vehiculo.valueOf(tipoVehiculo), dniConductor)
                    .withMarca(marca)
                    .withColor(color)
                    .build();

            // Intentar actualizar el vehículo
            vehiculoDAO.actualizarVehiculo(vehiculo);
            vehiculoDAO.close();
            resultado.put("success", "Vehículo actualizado con éxito: " + numeroPlaca);
        } catch (Exception e) {
            ErrorLog.log("Error al actualizar vehículo: " + e.getMessage(), ErrorLog.Level.WARN);
            resultado.put("error", "No se encontró un vehículo con la placa: " + numeroPlaca);
        }
        return resultado;
    }

    @DELETE
    @Path("/eliminado/{numeroPlaca}")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> eliminarVehiculo(@PathParam("numeroPlaca") String numeroPlaca) throws SQLException, NamingException, IOException {
        HashMap<String, String> resultado = new HashMap<>();
        VehiculoDAO vehiculoDAO = new VehiculoDAO();

        try {
            vehiculoDAO.eliminarVehiculo(numeroPlaca);
            resultado.put("success", "Vehículo eliminado con éxito: " + numeroPlaca);
        } catch (Exception e) {
            ErrorLog.log("Error al eliminar vehículo: " + e.getMessage(), ErrorLog.Level.ERROR);
            resultado.put("error", "No se encontró un vehículo con la placa: " + numeroPlaca);
        }
        return resultado;
    }
}