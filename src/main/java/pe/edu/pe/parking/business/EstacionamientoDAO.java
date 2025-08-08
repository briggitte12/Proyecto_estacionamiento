package pe.edu.pe.parking.business;

import pe.edu.pe.parking.model.Estacionamiento;
import pe.edu.pe.parking.service.AppConfig;
import pe.edu.pe.parking.util.DataAccessMariaDB;
import pe.edu.pe.parking.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class EstacionamientoDAO {
    private final Connection cnn;

    public EstacionamientoDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(
                DataAccessMariaDB.TipoDA.DATASOURCE,  AppConfig.getDatasource());
    }

    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    public Estacionamiento getEstacionamiento() throws IOException {
        Estacionamiento estacionamiento = null;
        String sql = "{CALL GetAllEstacionamiento()}";

        try (CallableStatement cs = cnn.prepareCall(sql)) {
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                Integer id_estacionamiento = rs.getInt("id_estacionamiento");
                String nombre = rs.getString("nombre");
                Integer capacidad_total = rs.getInt("capacidad_total");
                Integer nro_pisos = rs.getInt("numero_pisos");
                LocalTime horario_apertura = LocalTime.parse(rs.getString("horario_apertura"));
                LocalTime horario_cierre = LocalTime.parse(rs.getString("horario_cierre"));

                estacionamiento = new Estacionamiento(id_estacionamiento, nombre, capacidad_total, nro_pisos, horario_apertura, horario_cierre);
            }
        } catch (SQLException e) {
            ErrorLog.log("Error al ejecutar el procedimiento: " + e.getMessage(), ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
        return estacionamiento;
    }
}
