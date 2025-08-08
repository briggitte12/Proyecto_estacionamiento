package pe.edu.pe.parking.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class Estacionamiento implements Serializable {

    private int id_estacionamiento;
    private String nombre;
    private int capacidad_total;
    private int nro_pisos;
    private LocalTime horario_apertura;
    private LocalTime horario_cierre;

    public Estacionamiento() {
        id_estacionamiento = 0;
        nombre = "";
        capacidad_total = 0;
        nro_pisos = 0;
        horario_apertura = null;
        horario_cierre = null;
    }

    public Estacionamiento(int id_estacionamiento, String nombre, int capacidad_total, int nro_pisos, LocalTime horario_apertura, LocalTime horario_cierre) {
        this.id_estacionamiento = id_estacionamiento;
        this.nombre = nombre;
        this.capacidad_total = capacidad_total;
        this.nro_pisos = nro_pisos;
        this.horario_apertura = horario_apertura;
        this.horario_cierre = horario_cierre;
    }

    public int getId_estacionamiento() {
        return id_estacionamiento;
    }

    public void setId_estacionamiento(int id_estacionamiento) {
        this.id_estacionamiento = id_estacionamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad_total() {
        return capacidad_total;
    }

    public void setCapacidad_total(int capacidad_total) {
        this.capacidad_total = capacidad_total;
    }

    public int getNro_pisos() {
        return nro_pisos;
    }

    public void setNro_pisos(int nro_pisos) {
        this.nro_pisos = nro_pisos;
    }

    public LocalTime getHorario_apertura() {
        return horario_apertura;
    }

    public void setHorario_apertura(LocalTime horario_apertura) {
        this.horario_apertura = horario_apertura;
    }

    public LocalTime getHorario_cierre() {
        return horario_cierre;
    }

    public void setHorario_cierre(LocalTime horario_cierre) {
        this.horario_cierre = horario_cierre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estacionamiento that = (Estacionamiento) o;
        return id_estacionamiento == that.id_estacionamiento && capacidad_total == that.capacidad_total && nro_pisos == that.nro_pisos && Objects.equals(nombre, that.nombre) && Objects.equals(horario_apertura, that.horario_apertura) && Objects.equals(horario_cierre, that.horario_cierre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_estacionamiento, nombre, capacidad_total, nro_pisos, horario_apertura, horario_cierre);
    }
}
