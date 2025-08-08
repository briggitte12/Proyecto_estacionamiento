package pe.edu.pe.parking.model;

import pe.edu.pe.parking.service.GestorDeRoles;
import pe.edu.pe.parking.service.RolPrototype;

import java.io.Serializable;

public class Usuario implements Serializable {

    // Atributos
    private int id_usuario;
    private String usuario;
    private String clave;
    private String rol;
    private String nombre_completo;
    private String correo;
    private String telefono;
    private String foto;
    // Prototype
    private RolPrototype roles;

    // Constructor Vacío
    public Usuario() {
        id_usuario = 0;
        usuario = "";
        clave = "";
        rol = "";
        nombre_completo = "";
        correo = "";
        telefono = "";
        foto = "";
        roles = null;
    }

    // Constructor Lleno
    public Usuario(int id_usuario, String usuario, String clave, String rol, String nombre_completo, String correo, String telefono, String foto) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
        this.nombre_completo = nombre_completo;
        this.correo = correo;
        this.telefono = telefono;
        this.foto = foto;

        // Asignar el rol
        GestorDeRoles gestorDeRoles = new GestorDeRoles();
        this.roles = gestorDeRoles.obtenerRol(rol);
    }

    // Verificar
    public boolean puedeAccederAPagina(String pagina) {
        return roles.tienePermiso(pagina);
    }

    public Usuario(int id_usuario, String usuario, String nombre_completo) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.nombre_completo = nombre_completo;
    }

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;

        // Actualizar el rol
        GestorDeRoles gestorDeRoles = new GestorDeRoles();
        this.roles = gestorDeRoles.obtenerRol(rol);
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
