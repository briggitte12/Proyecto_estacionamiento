package pe.edu.pe.parking.business;

import pe.edu.pe.parking.model.Conductor;
import pe.edu.pe.parking.service.AppConfig;
import pe.edu.pe.parking.util.DataAccessMariaDB;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class ConductorDAO {

    private final Connection cnn;

    public ConductorDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(
                DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
    }

    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    public List<Conductor> gettAllConductores() {
        List<Conductor> lista = new LinkedList<>();
        String sql = "{CALL GetAllConductores()}";  // Llamada al procedimiento almacenado

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            ResultSet rs = cs.executeQuery();
            int cont = 0;

            while (rs.next()) {
                String dni_conductor = rs.getString("dni_conductor");
                String nombre_completo = rs.getString("nombre_completo");
                String celular = rs.getString("celular");
                String correo = rs.getString("correo");
                String direccion = rs.getString("direccion");

                // Genero ENUM
                String generoString = rs.getString("genero");
                Conductor.Genero genero = Conductor.Genero.valueOf(generoString);

                // Fecha de nacimiento
                String fecha_nacimientoString = rs.getString("fecha_nacimiento");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha_nacimiento = LocalDate.parse(fecha_nacimientoString, formatter);

                // Crear el objeto Conductor usando el Builder
                Conductor conductor = new Conductor.Builder(dni_conductor, nombre_completo, celular, correo, fecha_nacimiento, genero)
                        .withdireccion(direccion)
                        .build();

                lista.add(conductor);
                cont++;
            }

            if (cont > 0) {
                ErrorLog.log("Se han recuperado " + cont + " conductores", ErrorLog.Level.INFO);
            }

        } catch (SQLException | IOException e) {
            try {
                ErrorLog.log("Error al ejecutar el procedimiento: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return lista;
    }


    public Conductor getConductorByDni(String dniConductor) {
        String sql = "{CALL getConductorByDni(?)}";  // Llamada al procedimiento almacenado
        Conductor conductor = null;

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setString(1, dniConductor);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    String nombre_completo = rs.getString("nombre_completo");
                    String celular = rs.getString("celular");
                    String correo = rs.getString("correo");
                    String direccion = rs.getString("direccion");

                    String generoString = rs.getString("genero");
                    Conductor.Genero genero = Conductor.Genero.valueOf(generoString);

                    String fecha_nacimientoString = rs.getString("fecha_nacimiento");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fecha_nacimiento = LocalDate.parse(fecha_nacimientoString, formatter);

                    conductor = new Conductor.Builder(dniConductor, nombre_completo, celular, correo, fecha_nacimiento, genero)
                            .withdireccion(direccion)
                            .build();
                }
            }
        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al obtener conductor por DNI: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return conductor;
    }

    public boolean updateConductor(Conductor conductor) {
        String sql = "{CALL ActualizarConductor(?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setString(1, conductor.getDni_conductor());
            cs.setString(2, conductor.getNombre_completo());
            cs.setString(3, conductor.getCelular());
            cs.setString(4, conductor.getCorreo());
            cs.setDate(5, java.sql.Date.valueOf(conductor.getFecha_nacimiento()));
            cs.setString(6, conductor.getDireccion());
            cs.setString(7, conductor.getGenero().name());

            int filas = cs.executeUpdate();
            if (filas > 0) {
                ErrorLog.log("Conductor actualizado: " + conductor.getDni_conductor(), ErrorLog.Level.INFO);
                return true;
            }

        } catch (SQLException | IOException e) {
            try {
                ErrorLog.log("Error al actualizar conductor: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean RegistrarConductor(Conductor conductor) {
        String sql = "{CALL RegistrarConductor(?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setString(1, conductor.getDni_conductor());
            cs.setString(2, conductor.getNombre_completo());
            cs.setString(3, conductor.getCelular());
            cs.setString(4, conductor.getCorreo());
            cs.setDate(5, java.sql.Date.valueOf(conductor.getFecha_nacimiento()));
            cs.setString(6, conductor.getDireccion());
            cs.setString(7, conductor.getGenero().name());

            cs.execute();

            ErrorLog.log("Conductor registrado: " + conductor.getDni_conductor(), ErrorLog.Level.INFO);

        } catch (SQLException | IOException e) {
            try {
                ErrorLog.log("Error al registrar conductor: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return false;
    }

    // Verificación de si el DNI existe
    public boolean dniExistente(String dni_conductor) throws SQLException {
        if (dni_conductor == null || dni_conductor.isEmpty()) {
            return false;
        }

        try (CallableStatement stmt = cnn.prepareCall("{CALL verificarDNIExistente(?)}")) {
            stmt.setString(1, dni_conductor);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar DNI: " + e.getMessage());
            throw e;
        }
    }

    // Obtener la cantidad de conductores
    public int getCantidadConductores() throws SQLException, NamingException, IOException {
        String sql = "{CALL GetCantidadConductores()}";
        int cantidad = 0;

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
        } catch (SQLException e) {
            ErrorLog.log("Error al ejecutar el procedimiento para obtener la cantidad de conductores: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw e;
        }

        return cantidad;
    }

    public boolean eliminarConductor(String dniConductor) {
        String sql = "{CALL EliminarConductor(?)}";
        try (CallableStatement cs = cnn.prepareCall(sql)) {
            // Establecer el parámetro DNI
            cs.setString(1, dniConductor);

            // Ejecutar el procedimiento
            int filasAfectadas = cs.executeUpdate();
            if (filasAfectadas > 0) {
                ErrorLog.log("Conductor eliminado: " + dniConductor, ErrorLog.Level.INFO);
                return true;
            } else {
                ErrorLog.log("No se encontró el conductor con DNI: " + dniConductor, ErrorLog.Level.WARN);
            }
        } catch (SQLException | IOException e) {
            try {
                ErrorLog.log("Error al eliminar conductor: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return false;
    }
}
