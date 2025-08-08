package pe.edu.pe.parking.business;

import pe.edu.pe.parking.model.Registro_Vehiculos;
import pe.edu.pe.parking.service.AppConfig;
import pe.edu.pe.parking.util.DataAccessMariaDB;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Ingreso_VehiculosDAO {

    private final Connection cnn;

    public Ingreso_VehiculosDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE,  AppConfig.getDatasource());
    }

    public void close() throws SQLException {
        if (cnn != null) {
            cnn.close();
        }
    }

    public List<Registro_Vehiculos> getAllRegistroVehiculo() throws IOException {
        List<Registro_Vehiculos> lista = new LinkedList<>();
        String sql = "{CALL GetAllRegistrosIngreso()}";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (CallableStatement cs = cnn.prepareCall(sql); ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Registro_Vehiculos.Builder builder = new Registro_Vehiculos.Builder(
                        rs.getString("numero_placa"),
                        rs.getInt("id_estacionamiento"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_tarifa"),
                        rs.getInt("piso"),
                        LocalDateTime.parse(rs.getString("fecha_ingreso"), dateTimeFormatter)
                );

                if (rs.getString("fecha_salida") != null) {
                    builder.withFechaSalida(LocalDateTime.parse(rs.getString("fecha_salida"), dateTimeFormatter));
                }

                lista.add(builder
                        .withIdRegistro(rs.getInt("id_registro"))
                        .withObservacion(rs.getString("observacion"))
                        .withEstado(rs.getString("estado"))
                        .build()
                );
            }
            ErrorLog.log("Se han recuperado " + lista.size() + " registros", ErrorLog.Level.INFO);
        } catch (SQLException e) {
            ErrorLog.log("Error al ejecutar el procedimiento: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
        return lista;
    }

    public void registrarIngreso(Registro_Vehiculos registro, int idUsuario) throws SQLException, IOException {
        String sql = "{CALL RegistrarIngreso(?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setString(1, registro.getPlaca());
            cs.setInt(2, registro.getId_estacionamiento());
            cs.setInt(3, idUsuario);
            cs.setInt(4, registro.getId_tarifa());
            cs.setInt(5, registro.getPiso());
            cs.setString(6, registro.getObservacion());
            cs.setString(7, registro.getEstado());
            cs.execute();
        } catch (SQLException e) {
            ErrorLog.log("Error al ejecutar el procedimiento RegistrarIngreso: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
    }

    public void actualizarFechaSalida(int idRegistro, LocalDateTime fechaSalida) throws SQLException, IOException {
        String sql = "{CALL ActualizarFechaSalida(?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setInt(1, idRegistro);
            cs.setString(2, fechaSalida.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            cs.execute();
        } catch (SQLException e) {
            ErrorLog.log("Error al ejecutar el procedimiento ActualizarFechaSalida: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
    }

    public boolean eliminarIngreso(int idRegistro) throws IOException {
        String sql = "{CALL EliminarIngreso(?)}";

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            // Establecer el parámetro del procedimiento almacenado
            cs.setInt(1, idRegistro);

            // Ejecutar la consulta y verificar si se afectaron filas
            int filasAfectadas = cs.executeUpdate();
            if (filasAfectadas > 0) {
                ErrorLog.log("Registro de ingreso con ID " + idRegistro + " eliminado con éxito.", ErrorLog.Level.INFO);
                return true;
            }
        } catch (SQLException | IOException e) {
            ErrorLog.log("Error al ejecutar el procedimiento EliminarIngreso: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new RuntimeException("Error al eliminar el registro de ingreso: " + e.getMessage());
        }
        return false;
    }
}
