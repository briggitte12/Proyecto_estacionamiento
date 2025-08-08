package pe.edu.pe.parking.business;

import pe.edu.pe.parking.model.Cobro;
import pe.edu.pe.parking.service.AppConfig;
import pe.edu.pe.parking.util.DataAccessMariaDB;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CobroDAO {

    private final Connection cnn;

    public CobroDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(
                DataAccessMariaDB.TipoDA.DATASOURCE,  AppConfig.getDatasource());
    }

    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    public List<Cobro> getAllCobro() {
        List<Cobro> lista = new LinkedList<>();
        String sql = "{CALL GetAllCobros()}";  // Llamada al procedimiento almacenado

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            ResultSet rs = cs.executeQuery();
            int cont = 0;

            while (rs.next()) {
                Integer id_cobro = rs.getInt("id_cobro");
                Integer id_registro = rs.getInt("id_registro");
                Integer id_tarifa = rs.getInt("id_tarifa");
                Integer minutos_estacionados = rs.getInt("minutos_estacionado");
                Double monto_total = rs.getDouble("monto_total");
                //INNER JOIN
                String nombre_conductor = rs.getString("nombre_completo");
                String dni_conductor = rs.getString("dni_conductor");
                String placa = rs.getString("placa");
                String tipo_vehiculo = rs.getString("tipo_vehiculo");

                Cobro cobro = new Cobro(id_cobro, id_registro, id_tarifa, minutos_estacionados,
                        monto_total, nombre_conductor, dni_conductor, placa, tipo_vehiculo);
                lista.add(cobro);
                cont++;

            }

            // Log de éxito
            if (cont > 0) {
                ErrorLog.log("Se han recuperado " + cont + " cobro", ErrorLog.Level.INFO);
            }

        } catch (SQLException e) {
            // Log de error
            try {
                ErrorLog.log("Error al ejecutar el procedimiento: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public boolean registrarCobro(Cobro cobro) {
        String sql = "{CALL RegistrarCobro(?, ?, ?, ?)}";
        boolean registrado = false;

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setInt(1, cobro.getId_registro());
            cs.setInt(2, cobro.getId_tarifa());
            cs.setInt(3, cobro.getMinutos_estacionado());
            cs.setDouble(4, cobro.getMonto_total());

            registrado = cs.executeUpdate() > 0;

            // Log de éxito
            if (registrado) {
                ErrorLog.log("Cobro registrado exitosamente", ErrorLog.Level.INFO);
            }

        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al registrar el cobro: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return registrado;
    }

    public double getTotalGanancia() throws SQLException, IOException {
        String sql = "{CALL GetTotalGanancia()}";
        double totalGanancia = 0.0;

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                totalGanancia = rs.getDouble("total_ganancias");
            }
        } catch (SQLException e) {
            ErrorLog.log("Error al ejecutar el procedimiento para obtener el total de ganancia: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw e;
        }

        return totalGanancia;
    }

    public boolean actualizarCobro(int cobroId, int nuevosMinutos, double nuevoMonto) throws SQLException {
        String sql = "{CALL ActualizarCobro(?, ?, ?)}";
        boolean actualizado = false;

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setInt(1, cobroId);
            cs.setInt(2, nuevosMinutos);
            cs.setDouble(3, nuevoMonto);

            int filasActualizadas = cs.executeUpdate();
            actualizado = filasActualizadas > 0;
        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al actualizar el cobro: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new SQLException("Error al ejecutar el procedimiento ActualizarCobro()", e);
        }
        return actualizado;
    }

    public boolean eliminarCobro(int cobroId) throws SQLException, IOException {
        String sql = "{CALL EliminarCobro(?)}";
        boolean eliminado = false;

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setInt(1, cobroId);

            int filasEliminadas = cs.executeUpdate();
            eliminado = filasEliminadas > 0;

            // Log de éxito
            if (eliminado) {
                ErrorLog.log("Cobro eliminado exitosamente: ID " + cobroId, ErrorLog.Level.INFO);
            }
        } catch (SQLException | IOException e) {
            ErrorLog.log("Error al eliminar el cobro: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException("Error al eliminar el cobro", e);
        }
        return eliminado;
    }
}
