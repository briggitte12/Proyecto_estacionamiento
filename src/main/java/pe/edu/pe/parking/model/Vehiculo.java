package pe.edu.pe.parking.model;
import java.util.Objects;

public class Vehiculo  {
    private String numero_placa;
    private Tipo_Vehiculo tipo_vehiculo;
    private String marca;
    private String color;
    private String dni_conductor;

    // Enum para Tipo_Vehiculo
    public enum Tipo_Vehiculo {
        Carro,
        Camioneta,
        MotoLineal,
        MotoTaxi
    }

    public Vehiculo(Builder builder) {
        this.numero_placa = builder.numero_placa;
        this.tipo_vehiculo = builder.tipo_vehiculo;
        this.marca = builder.marca;
        this.color = builder.color;
        this.dni_conductor = builder.dni_conductor;
    }

    public static class Builder {
        private String numero_placa;
        private Tipo_Vehiculo tipo_vehiculo;
        private String marca;
        private String color;
        private String dni_conductor;

        public Builder(String numero_placa, Tipo_Vehiculo tipo_vehiculo, String dni_conductor) {
            this.numero_placa = numero_placa;
            this.tipo_vehiculo = tipo_vehiculo;
            this.dni_conductor = dni_conductor;
            //Muy Importante
            this.marca = "No especificado";
            this.color = "No especificado";
        }

        public Builder withMarca(String marca) {
            this.marca = marca;
            return this;
        }

        public Builder withColor(String color) {
            this.color = color;
            return this;
        }

        public Vehiculo build() {
            return new Vehiculo(this);
        }
    }

    // Getters y Setters
    public String getNumero_placa() {
        return numero_placa;
    }

    public void setNumero_placa(String numero_placa) {
        this.numero_placa = numero_placa;
    }

    public Tipo_Vehiculo getTipo_vehiculo() {
        return tipo_vehiculo;
    }

    public void setTipo_vehiculo(Tipo_Vehiculo tipo_vehiculo) {
        this.tipo_vehiculo = tipo_vehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDni_conductor() {
        return dni_conductor;
    }

    public void setDni_conductor(String dni_conductor) {
        this.dni_conductor = dni_conductor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(numero_placa, vehiculo.numero_placa) &&
                tipo_vehiculo == vehiculo.tipo_vehiculo &&
                Objects.equals(marca, vehiculo.marca) &&
                Objects.equals(color, vehiculo.color) &&
                Objects.equals(dni_conductor, vehiculo.dni_conductor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero_placa, tipo_vehiculo, marca, color, dni_conductor);
    }

    @Override
    public String toString() {
        return "Vehiculo {" +
                "numero_placa='" + numero_placa + '\'' +
                ", tipo_vehiculo=" + tipo_vehiculo +
                ", marca='" + marca + '\'' +
                ", color='" + color + '\'' +
                ", dni_conductor='" + dni_conductor + '\'' +
                '}';
    }
}
