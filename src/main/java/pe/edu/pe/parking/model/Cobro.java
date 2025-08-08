package pe.edu.pe.parking.model;

import java.io.Serializable;
import java.util.Objects;

public class Cobro implements Serializable {

    private Integer id_cobro;
    private Integer id_registro;
    private Integer id_tarifa;
    private Integer minutos_estacionado;
    private Double monto_total;

    //Atributos para inner join
    private String nombre_conductor;
    private String dni_conductor;
    private String placa_vehiculo;
    private String tipo_vehiculo;

    public Cobro() {
        id_cobro = null;
        id_registro = null;
        id_tarifa = null;
        minutos_estacionado = null;
        monto_total = null;
        nombre_conductor = "";
        dni_conductor = "";
        placa_vehiculo = "";
        tipo_vehiculo = "";
    }

    public Cobro(Integer id_cobro, Integer id_registro, Integer id_tarifa, Integer minutos_estacionado, Double monto_total, String nombre_conductor, String dni_conductor, String placa_vehiculo, String tipo_vehiculo) {
        this.id_cobro = id_cobro;
        this.id_registro = id_registro;
        this.id_tarifa = id_tarifa;
        this.minutos_estacionado = minutos_estacionado;
        this.monto_total = monto_total;
        this.nombre_conductor = nombre_conductor;
        this.dni_conductor = dni_conductor;
        this.placa_vehiculo = placa_vehiculo;
        this.tipo_vehiculo = tipo_vehiculo;
    }

    public Cobro(Integer id_registro, Integer id_tarifa, Integer minutos_estacionado, Double monto_total) {
        this.id_registro = id_registro;
        this.id_tarifa = id_tarifa;
        this.minutos_estacionado = minutos_estacionado;
        this.monto_total = monto_total;
    }

    public String getNombre_conductor() {
        return nombre_conductor;
    }

    public void setNombre_conductor(String nombre_conductor) {
        this.nombre_conductor = nombre_conductor;
    }

    public String getDni_conductor() {
        return dni_conductor;
    }

    public void setDni_conductor(String dni_conductor) {
        this.dni_conductor = dni_conductor;
    }

    public String getPlaca_vehiculo() {
        return placa_vehiculo;
    }

    public void setPlaca_vehiculo(String placa_vehiculo) {
        this.placa_vehiculo = placa_vehiculo;
    }

    public String getTipo_vehiculo() {
        return tipo_vehiculo;
    }

    public void setTipo_vehiculo(String tipo_vehiculo) {
        this.tipo_vehiculo = tipo_vehiculo;
    }

    public Integer getId_cobro() {
        return id_cobro;
    }

    public void setId_cobro(Integer id_cobro) {
        this.id_cobro = id_cobro;
    }

    public Integer getId_registro() {
        return id_registro;
    }

    public void setId_registro(Integer id_registro) {
        this.id_registro = id_registro;
    }

    public Integer getId_tarifa() {
        return id_tarifa;
    }

    public void setId_tarifa(Integer id_tarifa) {
        this.id_tarifa = id_tarifa;
    }

    public Integer getMinutos_estacionado() {
        return minutos_estacionado;
    }

    public void setMinutos_estacionado(Integer minutos_estacionado) {
        this.minutos_estacionado = minutos_estacionado;
    }

    public Double getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(Double monto_total) {
        this.monto_total = monto_total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cobro cobro = (Cobro) o;
        return Objects.equals(id_cobro, cobro.id_cobro) && Objects.equals(id_registro, cobro.id_registro) && Objects.equals(id_tarifa, cobro.id_tarifa) && Objects.equals(minutos_estacionado, cobro.minutos_estacionado) && Objects.equals(monto_total, cobro.monto_total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_cobro, id_registro, id_tarifa, minutos_estacionado, monto_total);
    }
}
