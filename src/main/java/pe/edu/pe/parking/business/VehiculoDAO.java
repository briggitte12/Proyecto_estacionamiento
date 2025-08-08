package pe.edu.pe.parking.business;

import pe.edu.pe.parking.model.Vehiculo;
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

public class VehiculoDAO {

    private final Connection cnn;

    public VehiculoDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(
                DataAccessMariaDB.TipoDA.DATASOURCE,  AppConfig.getDatasource());
    }

    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    public List<Vehiculo> getAllVehiculo() {
        List<Vehiculo> lista = new LinkedList<>();
        String sql = "{CALL GetAllVehiculo()}";  // Llamada al procedimiento almacenado

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            ResultSet rs = cs.executeQuery();
            int cont = 0;

            while (rs.next()) {
                String numero_placa = rs.getString("numero_placa");
                String marca = rs.getString("marca");
                String color = rs.getString("color");
                String dni_conductor = rs.getString("dni_conductor");

                // TIPO VEHICULO ENUM
                String vehiculoString = rs.getString("tipo_vehiculo");
                Vehiculo.Tipo_Vehiculo tipoVehiculo = Vehiculo.Tipo_Vehiculo.valueOf(vehiculoString);

                // Patrón Builder
                Vehiculo vehiculo = new Vehiculo.Builder(numero_placa,tipoVehiculo,dni_conductor)
                        .withColor(color)
                        .withMarca(marca)
                        .build();

                lista.add(vehiculo);
                cont++;
            }

            // Log de éxito si se muestran vehiculos
            if (cont > 0) {
                ErrorLog.log("Se han recuperado " + cont + " vehiculo(s)", ErrorLog.Level.INFO);
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

    public boolean RegistrarVehiculo(Vehiculo vehiculo) {
        String sql = "{CALL RegistrarVehiculo(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setString(1, vehiculo.getNumero_placa());
            cs.setString(2, vehiculo.getTipo_vehiculo().name());
            cs.setString(3, vehiculo.getMarca());
            cs.setString(4, vehiculo.getColor());
            cs.setString(5, vehiculo.getDni_conductor());
            cs.execute();

            ErrorLog.log("Vehículo registrado: " + vehiculo.getNumero_placa(), ErrorLog.Level.INFO);

        } catch (SQLException | IOException e) {
            try {
                ErrorLog.log("Error al registrar vehículo: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException("Error al registrar el vehículo: " + e.getMessage(), e);
        }
        return false;
    }

    public boolean placaExistente(String numero_placa) throws SQLException {
        try (CallableStatement stmt = cnn.prepareCall("{CALL verificarPlacaExistente(?)}")) {
            stmt.setString(1, numero_placa);
            ResultSet rs = stmt.executeQuery();
            // Retorna true si existe, false si no
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public int getCantidadVehiculos() throws SQLException, NamingException, IOException {
        String sql = "{CALL GetCantidadVehiculos()}";
        int cantidad_vehiculos = 0;

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                cantidad_vehiculos = rs.getInt("cantidad_vehiculos");
            }
        } catch (SQLException e) {
            ErrorLog.log("Error al ejecutar el procedimiento para obtener la cantidad de vehículos: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw e;
        }

        return cantidad_vehiculos;
    }

    public Vehiculo obtenerVehiculo(String numeroPlaca) {
        String sql = "{CALL ObtenerVehiculo(?)}";
        Vehiculo vehiculo = null;

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setString(1, numeroPlaca);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    String tipoVehiculoString = rs.getString("tipo_vehiculo");
                    String marca = rs.getString("marca");
                    String color = rs.getString("color");
                    String dni_conductor = rs.getString("dni_conductor");

                    // TIPO VEHICULO ENUM
                    Vehiculo.Tipo_Vehiculo tipoVehiculo = Vehiculo.Tipo_Vehiculo.valueOf(tipoVehiculoString);

                    // Builder
                    vehiculo = new Vehiculo.Builder(numeroPlaca, tipoVehiculo, dni_conductor)
                            .withColor(color)
                            .withMarca(marca)
                            .build();
                }
            }
        } catch (SQLException e) {
            try {
                ErrorLog.log("Error al obtener vehículo por número de placa: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return vehiculo;
    }

    public boolean actualizarVehiculo(Vehiculo vehiculo) {
        String sql = "{CALL ActualizarVehiculo(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setString(1, vehiculo.getNumero_placa());
            cs.setString(2, vehiculo.getTipo_vehiculo().name());
            cs.setString(3, vehiculo.getMarca());
            cs.setString(4, vehiculo.getColor());
            cs.setString(5, vehiculo.getDni_conductor());

            int filas = cs.executeUpdate();
            if (filas > 0) {
                ErrorLog.log("Vehículo actualizado: " + vehiculo.getNumero_placa(), ErrorLog.Level.INFO);
                return true;
            }

        } catch (SQLException | IOException e) {
            try {
                ErrorLog.log("Error al actualizar vehículo: " + e.getMessage(), ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean eliminarVehiculo(String numeroPlaca) {
        String sql = "{CALL EliminarVehiculo(?)}";

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            cs.setString(1, numeroPlaca);

            // Ejecutar la consulta y verificar si se afectaron filas
            int filasAfectadas = cs.executeUpdate();
            if (filasAfectadas > 0) {
                ErrorLog.log("Vehículo eliminado con éxito: " + numeroPlaca, ErrorLog.Level.INFO);
                return true;
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Error al eliminar el vehículo: " + e.getMessage());
        }
        return false;
    }
}
