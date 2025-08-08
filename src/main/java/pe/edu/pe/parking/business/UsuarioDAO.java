package pe.edu.pe.parking.business;

import pe.edu.pe.parking.model.Usuario;
import pe.edu.pe.parking.service.AppConfig;
import pe.edu.pe.parking.util.DataAccessMariaDB;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDAO {

    private final Connection cnn;

    public UsuarioDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(
                DataAccessMariaDB.TipoDA.DATASOURCE,  AppConfig.getDatasource());
    }

    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    public Usuario getUsuario(String username) throws IOException {
        Usuario usuario = null;
        String sql = "{CALL GetUsuarioByNombre(?)}";

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setString(1, username);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String user = rs.getString("usuario");
                String clave = rs.getString("clave");
                String nombre_completo = rs.getString("nombre_completo");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                String foto = rs.getString("foto");
                String rol = rs.getString("rol");

                usuario = new Usuario(id_usuario, user, clave, rol, nombre_completo, correo, telefono, foto);
            }
        } catch (SQLException e) {
            ErrorLog.log("Error al ejecutar el procedimiento: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
        return usuario;
    }

    public static boolean isValidUser(String usuario, String clave)
            throws SQLException, NamingException, IOException {

        String strSQL = String.format("CALL pr_checkUser('%s', '%s')", usuario, md5(clave));

        ErrorLog.log("Ejecutando consulta: " + strSQL, ErrorLog.Level.INFO);

        Connection cnn = null;
        ResultSet rst = null;

        try {
            // Crear conexión a la base de datos usando el DataSource
            cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/Maria");

            // Ejecutar la consulta
            rst = cnn.createStatement().executeQuery(strSQL);

            return rst.next();
        } catch (SQLException e) {
            // Log de error
            ErrorLog.log("Error al validar usuario: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw e;
        } finally {
            // Cerrar recursos
            if (rst != null) {
                rst.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
    }

    public int getCantidadUsuarios() throws SQLException, NamingException, IOException {
        String sql = "{CALL GetCantidadUsuarios()}";
        int cantidad_usuario = 0;

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                cantidad_usuario = rs.getInt("cantidad_usuario");
            }
        } catch (SQLException e) {
            ErrorLog.log("Error al ejecutar el procedimiento para obtener la cantidad de usuarios: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw e;
        }

        return cantidad_usuario;
    }

    private static String md5(String data) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            return byteArrayToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            return data;
        }
    }
    private static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public String agregarUsuario(Usuario usuario) {
        String sql = "{CALL agregarUsuario(?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setString(1, usuario.getUsuario());
            cs.setString(2, md5(usuario.getClave())); // La clave encriptada
            cs.setString(3, usuario.getRol());
            cs.setString(4, usuario.getNombre_completo());
            cs.setString(5, usuario.getCorreo());
            cs.setString(6, usuario.getTelefono());
            cs.setString(7, usuario.getFoto());

            // Ejecutar el procedimiento
            int filasAfectadas = cs.executeUpdate();
            if (filasAfectadas > 0) {
                ErrorLog.log("Usuario agregado: " + usuario.getUsuario(), ErrorLog.Level.INFO);
                return "Usuario " + usuario.getUsuario() + " agregado con éxito.";
            } else {
                ErrorLog.log("No se pudo agregar el usuario: " + usuario.getUsuario(), ErrorLog.Level.WARN);
            }
        } catch (SQLException | IOException e) {
            try {
                ErrorLog.log("Error al agregar usuario: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return "No se pudo agregar el usuario: " + usuario.getUsuario();
    }
}


