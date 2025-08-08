package pe.edu.pe.parking.rest;

import pe.edu.pe.parking.business.UsuarioDAO;
import pe.edu.pe.parking.model.RestResponse;
import pe.edu.pe.parking.model.Usuario;
import javax.validation.constraints.NotNull;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import pe.edu.pe.parking.util.ErrorLog;


@Path("/usuarios")
public class UsuarioResource {

    @GET @Path("/{nombre_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarioPorNombre(@PathParam("nombre_usuario") String nombreUsuario) throws SQLException, NamingException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            Usuario usuario = usuarioDAO.getUsuario(nombreUsuario);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"mensaje\": \"Usuario no encontrado.\"}").build();
            }
            usuarioDAO.close();
            return Response.ok(usuario).build();
        } catch (SQLException | IOException e) {
            ErrorLog.log("Ocurrió un error al obtener el usuario por su nombre." + e.getMessage(), ErrorLog.Level.ERROR);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Ocurrió un error al procesar la solicitud.\"}").build();
        }
    }

    @GET @Path("/actividad")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarioAutorizado(@HeaderParam("Authorization") String authorization) {
        if (authorization != null && !authorization.isEmpty()){
            try {
                String[] token = authorization.split(" ");
                String nombreUsuario = token[1];
                String clave = token[2];

                if (UsuarioDAO.isValidUser(nombreUsuario, clave)) {
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    Usuario usuario = usuarioDAO.getUsuario(nombreUsuario);

                    if (usuario == null) {
                        return Response.status(Response.Status.NOT_FOUND).entity("{\"mensaje\": \"Usuario no encontrado.\"}").build();
                    }

                    boolean es_admin = usuario.getRol().equals("Administrador");
                    if (es_admin) {
                        return Response.ok("actividad").build();
                    } else {
                        return Response.status(Response.Status.FORBIDDEN).entity("{\"mensaje\": \"El usuario no tiene el rol de administrador.\"}").build();
                    }
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("{\"mensaje\": \"El usuario es inválido.\"}").build();
                }
            } catch (SQLException | NamingException | IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"mensaje\": \"Ocurrió un error interno.\"}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse agregarUsuarioJson( @NotNull Usuario usuario) throws SQLException, NamingException {
        if (!usuario.getUsuario().equals("")){
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return new RestResponse(RestResponse.STATUS.SUCCESS, usuarioDAO.agregarUsuario(usuario));
        } else {
            return new RestResponse(RestResponse.STATUS.FAIL, "Usuario está vacío.");
        }
    }

    @POST @Path("/subido")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.TEXT_PLAIN)
    public Response cargarFoto(@NotNull @QueryParam("nombreArchivo") String nombreArchivo, @FormDataParam("archivo") InputStream archivo) throws IOException {
        try {
            java.nio.file.Path ruta = Paths.get("/tmp/" + nombreArchivo);
            Files.copy(archivo,ruta, StandardCopyOption.REPLACE_EXISTING);
            return Response.ok("Archivo copiado").build();
        } catch (IOException e){
            return Response.serverError().build();
        }
    }

    @GET @Path("/descargado")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response descargarFoto(@QueryParam("nombreArchivo") String nombreArchivo) throws IOException {
        File archivo = new File("/tmp/", nombreArchivo);
        if (!archivo.exists()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Archivo no encontrado").build();
        }
        Response.ResponseBuilder responseBuilder = Response.ok((Object) archivo);
        responseBuilder.header("Content-Disposition", "attachment;filename=" + nombreArchivo);
        return responseBuilder.build();
    }
}