package pe.edu.pe.parking.util;

import pe.edu.pe.parking.business.ConductorDAO;
import pe.edu.pe.parking.business.VehiculoDAO;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validar {

    private static ConductorDAO conductorDAO;
    private static VehiculoDAO vehiculoDAO;

    static {
        try {
            vehiculoDAO = new VehiculoDAO();
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
        try {
            conductorDAO = new ConductorDAO();
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{3,}$"); // Mínimo 3 letras y permite espacios
    private static final Pattern CELULAR_PATTERN = Pattern.compile("^\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    private static final Pattern PLACA_PATTERN = Pattern.compile("^[A-Z]{1,3}-\\d{3}$"); // Ejemplo de formato de placa
    private static final Pattern COLOR_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]{3,}$"); // Mínimo 3 letras
    private static final Pattern MARCA_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{3,}$"); // Mínimo 3 letras

    public static boolean validarDNI(String dni) {
        return dni != null && dni.length() == 8 && dni.matches("\\d{8}");
    }

    public static boolean validarNombre(String nombre) {
        return nombre != null && NAME_PATTERN.matcher(nombre).matches();
    }

    public static boolean validarCelular(String celular) {
        return CELULAR_PATTERN.matcher(celular).matches();
    }

    public static boolean validarCorreo(String correo) {
        return correo != null && EMAIL_PATTERN.matcher(correo).matches();
    }

    public static boolean validarFechaNacimiento(LocalDate fecha) {
        return fecha != null && !fecha.isAfter(LocalDate.now());
    }

    public static boolean validarPlaca(String placa) {
        return placa != null && PLACA_PATTERN.matcher(placa).matches();
    }

    public static boolean validarColor(String color) {
        return color != null && COLOR_PATTERN.matcher(color).matches();
    }

    public static boolean validarMarca(String marca) {
        return marca != null && MARCA_PATTERN.matcher(marca).matches();
    }

    private static boolean esCampoVacio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public static boolean validarMontoTotal(String montoTotalStr) throws ServletException {
        if (montoTotalStr == null || montoTotalStr.trim().isEmpty()) {
            throw new ServletException("El monto total no puede estar vacío.");
        }
        if (!montoTotalStr.matches("^S/\\.\\d+(\\.\\d{1,2})?$")) {
            throw new ServletException("Formato de monto total inválido.");
        }
        return true;
    }

    public static boolean validarDatosConductor(String dni, String nombre, String celular, String correo, String fechaNacimientoStr) throws ServletException {
        if (esCampoVacio(dni) || esCampoVacio(nombre) || esCampoVacio(celular) || esCampoVacio(correo) || esCampoVacio(fechaNacimientoStr)) {
            throw new IllegalArgumentException("Ningún campo puede estar vacío.");
        }

        LocalDate fechaNacimiento;
        try {
            fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
        } catch (DateTimeParseException e) {
            throw new ServletException("La fecha de nacimiento es inválida.");
        }

        if (!validarDNI(dni)) {
            throw new ServletException("El DNI debe tener exactamente 8 dígitos.");
        }
        if (!validarNombre(nombre)) {
            throw new ServletException("El nombre completo debe tener al menos 3 letras y no puede contener números ni signos no alfabéticos.");
        }
        if (!validarCelular(celular)) {
            throw new ServletException("El celular debe contener exactamente 9 dígitos.");
        }
        if (!validarCorreo(correo)) {
            throw new ServletException("El correo electrónico es inválido.");
        }
        if (!validarFechaNacimiento(fechaNacimiento)) {
            throw new ServletException("La fecha de nacimiento debe ser una fecha pasada y no puede ser hoy.");
        }

        try {
            if (conductorDAO.dniExistente(dni)) {
                throw new ServletException("El DNI ya está registrado.");
            }
        } catch (SQLException e) {
            throw new ServletException("Error al verificar el DNI en la base de datos.", e);
        }

        return true;
    }

    public static boolean validarDatosVehiculo(String numeroPlaca, String marca, String color) throws ServletException {
        if (esCampoVacio(numeroPlaca) || esCampoVacio(marca) || esCampoVacio(color)) {
            throw new ServletException("Ningún campo del vehículo puede estar vacío.");
        }

        if (!validarPlaca(numeroPlaca)) {
            //ABC-123 (EJEMPLO)
            throw new ServletException("El número de placa es inválido.");
        }
        if (!validarMarca(marca)) {
            throw new ServletException("La marca debe tener al menos 3 letras y solo debe contener letras.");
        }
        if (!validarColor(color)) {
            throw new ServletException("El color debe tener al menos 3 letras y no puede contener números ni estar vacío.");
        }

        // Verifica si la placa ya está registrada
        try {
            if (vehiculoDAO.placaExistente(numeroPlaca)) {
                throw new ServletException("La placa ya está registrada.");
            }
        } catch (SQLException e) {
            throw new ServletException("Error al verificar la placa en la base de datos.", e);
        }

        return true;
    }
}
