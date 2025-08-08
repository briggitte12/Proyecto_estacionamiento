package pe.edu.pe.parking.model;

import java.time.LocalDate;

public class Conductor {

    //Atributos
    private String dni_conductor;
    private String nombre_completo;
    private String celular;
    private String correo;
    private LocalDate fecha_nacimiento;
    private String direccion;
    private Genero genero;
    //Atributo Extra
    private boolean add_vehiculo;


    public Conductor(Builder builder) {
        this.dni_conductor = builder.dni_conductor;
        this.nombre_completo = builder.nombre_completo;
        this.celular = builder.celular;
        this.correo = builder.correo;
        this.fecha_nacimiento = builder.fecha_nacimiento;
        this.direccion = builder.direccion;
        this.genero = builder.genero;
        this.add_vehiculo = builder.add_vehiculo;
    }
    // Enum para Genero
    public enum Genero {
        Masculino,
        Femenino
    }

    public Conductor(String dni_conductor, String nombre_completo, String celular, String correo, LocalDate fecha_nacimiento, String direccion, Genero genero) {
        this.dni_conductor = dni_conductor;
        this.nombre_completo = nombre_completo;
        this.celular = celular;
        this.correo = correo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.direccion = direccion;
        this.genero = genero;
    }

    public static class Builder {
        private String dni_conductor;
        private String nombre_completo;
        private String celular;
        private String correo;
        private LocalDate fecha_nacimiento;
        private String direccion;
        private Genero genero;
        private boolean add_vehiculo;

        public Builder(String dni_conductor, String nombre_completo, String celular, String correo, LocalDate fecha_nacimiento, Genero genero) {
            this.dni_conductor = dni_conductor;
            this.nombre_completo = nombre_completo;
            this.celular = celular;
            this.correo = correo;
            this.fecha_nacimiento = fecha_nacimiento;
            this.genero = genero;
            //Muy Importante
            this.add_vehiculo = false;
            this.direccion = "no";
        }

        public Builder withdireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder otro_vehiculo() {
            this.add_vehiculo = true;
            return this;
        }

        public Conductor build() {
            return new Conductor(this);
        }


    } //Fin Builder Class

    //Gett And Setter
    public String getDni_conductor() {
        return dni_conductor;
    }

    public void setDni_conductor(String dni_conductor) {
        this.dni_conductor = dni_conductor;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public boolean isAdd_vehiculo() {
        return add_vehiculo;
    }

    public void setAdd_vehiculo(boolean add_vehiculo) {
        this.add_vehiculo = add_vehiculo;
    }
} //Fin Class

