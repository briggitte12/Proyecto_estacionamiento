package pe.edu.pe.parking.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v2")
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> r = new HashSet<>();
        r.add(UsuarioResource.class);
        return r;
    }
}