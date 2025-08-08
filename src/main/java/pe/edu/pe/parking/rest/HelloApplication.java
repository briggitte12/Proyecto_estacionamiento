package pe.edu.pe.parking.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class HelloApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> h = new HashSet<>();
        h.add(VehiculoResource.class);
        h.add(TarifaResource.class);
        h.add(CobroResource.class);
        h.add(ConductorResource.class);
        h.add(IngresoVehiculosResource.class);
        return h;
    }
}