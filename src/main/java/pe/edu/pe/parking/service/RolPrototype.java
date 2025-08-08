package pe.edu.pe.parking.service;

public interface RolPrototype extends Cloneable {
    boolean tienePermiso(String pagina);
    RolPrototype clonar();
}