package pe.edu.pe.parking.business;

import pe.edu.pe.parking.model.Tarifa;
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

public class TarifaDAO {

    private final Connection cnn;

    public TarifaDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(
                DataAccessMariaDB.TipoDA.DATASOURCE,  AppConfig.getDatasource());
    }

    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    public List<Tarifa> gettAllTarifa() {
        List<Tarifa> lista = new LinkedList<>();
        String sql = "{CALL GetAllTarifa()}";  // Llamada al procedimiento almacenado

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            ResultSet rs = cs.executeQuery();
            int cont = 0;

            while (rs.next()) {
                Integer id_tarifa = rs.getInt("id_tarifa");
                Double precio_por_minuto = rs.getDouble("precio_por_minuto");

                //TIPO VEHICULO ENUM
                String vehiculoString = rs.getString("tipo_vehiculo");
                Tarifa.TipoVehiculo tipoVehiculo = Tarifa.TipoVehiculo.valueOf(vehiculoString);

                Tarifa tarifa = new Tarifa(id_tarifa, tipoVehiculo, precio_por_minuto);
                lista.add(tarifa);
                cont++;
            }

            // Log de éxito si se muestra tarifa
            if (cont > 0) {
                ErrorLog.log("Se han recuperado " + cont + " tarifa", ErrorLog.Level.INFO);
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

    // Metodo para obtener precio de cada tarifa
    public double getPrecioPorMinuto(int idTarifa) throws SQLException {
        String sql = "{CALL ObtenerPrecioPorMinuto(?)}"; // Llama al procedimiento almacenado
        double precioPorMinuto = 0.0;

        try (CallableStatement callableStatement = cnn.prepareCall(sql)) {
            callableStatement.setInt(1, idTarifa);

            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                precioPorMinuto = resultSet.getDouble("precio_por_minuto");
            }
        }

        return precioPorMinuto;
    }

    public boolean updateTarifa(int idTarifa, double nuevoPrecio) throws SQLException {
        String sql = "{CALL ActualizarTarifa(?, ?)}";

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setInt(1, idTarifa);
            cs.setDouble(2, nuevoPrecio);

            int filasActualizadas = cs.executeUpdate();

            return filasActualizadas > 0;

        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al actualizar tarifa: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new SQLException("Error al actualizar la tarifa", e);
        }
    }

    public Tarifa GetByIdTarifa(int idTarifa) throws SQLException {
        String sql = "{CALL GetByIdTarifa(?)}";
        Tarifa tarifa = null;

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setInt(1, idTarifa);

            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                // Obtener
                int id = rs.getInt("id_tarifa");
                double precioPorMinuto = rs.getDouble("precio_por_minuto");

                tarifa = new Tarifa(id, precioPorMinuto);
            }

        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al obtener tarifa por ID: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new SQLException("Error al obtener la tarifa", e);
        }

        return tarifa;
    }

    public boolean registrarTarifa(Tarifa tarifa) throws SQLException {
        String sql = "{CALL RegistrarTarifa(?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(sql)) {
            // Establecer los parámetros
            cs.setString(1, tarifa.getTipo_vehiculo().name());
            cs.setDouble(2, tarifa.getPrecio_por_minuto());

            // Ejecutar el procedimiento almacenado
            int filasInsertadas = cs.executeUpdate();

            return filasInsertadas > 0;
        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al registrar tarifa: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new SQLException("Error en el procedimiento RegistrarTarifa", e);
        }
    }

    public boolean eliminarTarifaPorTipoVehiculo(Tarifa.TipoVehiculo tipoVehiculo) throws SQLException {
        String sql = "{CALL EliminarTarifaPorTipoVehiculo(?)}";
        try (CallableStatement cs = cnn.prepareCall(sql)) {
            // Establecer el parámetro
            cs.setString(1, tipoVehiculo.name());

            // Ejecutar el procedimiento almacenado
            int filasEliminadas = cs.executeUpdate();

            return filasEliminadas > 0;
        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al eliminar tarifa por tipo de vehículo: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new SQLException("Error al eliminar la tarifa por tipo de vehículo", e);
        }
    }
}
