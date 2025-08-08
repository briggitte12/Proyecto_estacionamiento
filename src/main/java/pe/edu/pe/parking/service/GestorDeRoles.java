package pe.edu.pe.parking.service;

import pe.edu.pe.parking.service.RolAdministrador;
import pe.edu.pe.parking.service.RolAsistente;
import pe.edu.pe.parking.service.RolPrototype;

import java.util.HashMap;
import java.util.Map;

public class GestorDeRoles {

    // Mapa para almacenar los prototipos de roles
    private static final Map<String, RolPrototype> mapaDeRoles = new HashMap<>();

    // Inicializar
    static {
        mapaDeRoles.put("Administrador", new RolAdministrador());
        mapaDeRoles.put("Asistente", new RolAsistente());
    }

    // Obtener el prototipo del rol y clonarlo
    public RolPrototype obtenerRol(String tipoRol) {
        RolPrototype prototipo = mapaDeRoles.get(tipoRol);

        if (prototipo == null) {
            throw new IllegalArgumentException("Rol no válido: " + tipoRol);
        }

        return prototipo.clonar();
    }
}

