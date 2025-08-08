package pe.edu.pe.parking.model;

import java.io.Serializable;
import java.util.Objects;

public class Tarifa implements Serializable {

    private int id_tarifa;
    private TipoVehiculo tipo_vehiculo;
    private Double precio_por_minuto;

    // Enum para TipoVehiculo
    public enum TipoVehiculo {
        Carro,
        Camioneta,
        MotoLineal,
        MotoTaxi
    }

    public Tarifa() {
        id_tarifa = 0;
        tipo_vehiculo = TipoVehiculo.MotoTaxi;
        precio_por_minuto = null;
    }

    public Tarifa(int id_tarifa, TipoVehiculo tipo_vehiculo, Double precio_por_minuto) {
        this.id_tarifa = id_tarifa;
        this.tipo_vehiculo = tipo_vehiculo;
        this.precio_por_minuto = precio_por_minuto;
    }

    public Tarifa(TipoVehiculo tipo_vehiculo, Double precio_por_minuto) {
        this.tipo_vehiculo = tipo_vehiculo;
        this.precio_por_minuto = precio_por_minuto;
    }

    public Tarifa(int id_tarifa, Double precio_por_minuto) {
        this.id_tarifa = id_tarifa;
        this.precio_por_minuto = precio_por_minuto;
    }


    public int getId_tarifa() {
        return id_tarifa;
    }

    public void setId_tarifa(int id_tarifa) {
        this.id_tarifa = id_tarifa;
    }

    public Double getPrecio_por_minuto() {
        return precio_por_minuto;
    }

    public void setPrecio_por_minuto(Double precio_por_minuto) {
        this.precio_por_minuto = precio_por_minuto;
    }

    public TipoVehiculo getTipo_vehiculo() {
        return tipo_vehiculo;
    }

    public void setTipo_vehiculo(TipoVehiculo tipo_vehiculo) {
        this.tipo_vehiculo = tipo_vehiculo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarifa tarifa = (Tarifa) o;
        return id_tarifa == tarifa.id_tarifa && tipo_vehiculo == tarifa.tipo_vehiculo && Objects.equals(precio_por_minuto, tarifa.precio_por_minuto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_tarifa, tipo_vehiculo, precio_por_minuto);
    }
}
