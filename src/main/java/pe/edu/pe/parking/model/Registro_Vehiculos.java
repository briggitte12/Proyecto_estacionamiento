package pe.edu.pe.parking.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Registro_Vehiculos {

    private int id_registro;
    private String placa;
    private int id_estacionamiento;
    private int piso;
    private LocalDateTime fecha_ingreso;
    private LocalDateTime fecha_salida;
    private String observacion;
    private String estado;
    private int id_usuario;
    private int id_tarifa;

    private Registro_Vehiculos(Builder builder) {
        this.id_registro = builder.id_registro;
        this.placa = builder.placa;
        this.id_estacionamiento = builder.id_estacionamiento;
        this.piso = builder.piso;
        this.fecha_ingreso = builder.fecha_ingreso;
        this.fecha_salida = builder.fecha_salida;
        this.observacion = builder.observacion;
        this.estado = builder.estado;
        this.id_usuario = builder.id_usuario;
        this.id_tarifa = builder.id_tarifa;
    }

    public static class Builder {
        private int id_registro;
        private String placa;
        private int id_estacionamiento;
        private int piso;
        private LocalDateTime fecha_ingreso;
        private LocalDateTime fecha_salida;
        private String observacion;
        private String estado;
        private int id_usuario;
        private int id_tarifa;

        public Builder(String placa, int id_estacionamiento, int id_usuario, int id_tarifa, int piso, LocalDateTime fecha_ingreso) {
            this.placa = placa;
            this.id_usuario = id_usuario;
            this.id_estacionamiento = id_estacionamiento;
            this.id_tarifa = id_tarifa;
            this.piso = piso;
            this.fecha_ingreso = fecha_ingreso;
            this.observacion = "Sin Observación";
            this.estado = "En Estacionamiento";
        }

        public Builder withIdRegistro(int id_registro) {
            this.id_registro = id_registro;
            return this;
        }

        public Builder withFechaSalida(LocalDateTime fecha_salida) {
            this.fecha_salida = fecha_salida;
            return this;
        }

        public Builder withObservacion(String observacion) {
            this.observacion = observacion;
            return this;
        }

        public Builder withEstado(String estado) {
            this.estado = estado;
            return this;
        }

        public Registro_Vehiculos build() {
            return new Registro_Vehiculos(this);
        }
    }

    // Getters y Setters
    public int getId_registro() {
        return id_registro;
    }

    public void setId_registro(int id_registro) {
        this.id_registro = id_registro;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getId_estacionamiento() {
        return id_estacionamiento = 1;
    }

    public void setId_estacionamiento(int id_estacionamiento) {
        this.id_estacionamiento = id_estacionamiento;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public LocalDateTime getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(LocalDateTime fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public LocalDateTime getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(LocalDateTime fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_tarifa() {
        return id_tarifa;
    }

    public void setId_tarifa(int id_tarifa) {
        this.id_tarifa = id_tarifa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registro_Vehiculos that = (Registro_Vehiculos) o;
        return id_registro == that.id_registro && piso == that.piso && Objects.equals(placa, that.placa) &&
                id_estacionamiento == that.id_estacionamiento && Objects.equals(fecha_ingreso, that.fecha_ingreso) &&
                Objects.equals(fecha_salida, that.fecha_salida) && Objects.equals(observacion, that.observacion) &&
                Objects.equals(estado, that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_registro, placa, id_estacionamiento, piso, fecha_ingreso, fecha_salida, observacion, estado);
    }
}
