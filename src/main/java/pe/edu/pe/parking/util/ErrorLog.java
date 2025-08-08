package pe.edu.pe.parking.util;

import pe.edu.pe.parking.service.AppConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLog {

    public enum Level {INFO, WARN, ERROR};

    public static String log(String msg, Level level) throws IOException {
        String filename = AppConfig.getErrorLogFile();  // Ruta al archivo de log

        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String tiempo = ldt.format(fmt);

        // Formateamos el evento
        String evento_fmt = "%s - %s - %s\r\n";
        String evento = String.format(evento_fmt, tiempo, level, msg);

        // Guardamos el log en el archivo
        Files.write(Paths.get(filename), evento.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        System.out.println(evento);

        return evento;
    }

}
