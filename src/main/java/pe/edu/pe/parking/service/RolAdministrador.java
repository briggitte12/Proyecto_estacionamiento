package pe.edu.pe.parking.service;

public class RolAdministrador implements RolPrototype {

    @Override
    public boolean tienePermiso(String pagina) {
        // Acceso a Todo
        return true;
    }

    @Override
    public RolPrototype clonar() {
        try {
            return (RolPrototype) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar el rol de Administrador", e);
        }
    }
}
