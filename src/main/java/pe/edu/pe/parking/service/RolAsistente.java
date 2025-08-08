package pe.edu.pe.parking.service;


public class RolAsistente implements RolPrototype {

    @Override
    public boolean tienePermiso(String pagina) {
        return pagina.equals("vehiculo") || pagina.equals("conductor")
                || pagina.equals("usuario") || pagina.equals("estacionamiento") ||
                pagina.equals("detalle");
    }

    @Override
    public RolPrototype clonar() {
        try {
            return (RolPrototype) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar el rol de Asistente", e);
        }
    }
}
