package pe.edu.pe.parking.service;

import java.util.ResourceBundle;

public class AppConfig {
    // Crear el ResourceBundle
    static ResourceBundle rb = ResourceBundle.getBundle("app");
	
	// Crear metodo getDataSource
    public static String getDatasource(){
        return rb.getString("datasource");
    }
    
    // Crear metodo getErrorLogFile
    public static String getErrorLogFile(){
        // completar aqui
        return rb.getString("errorLog");
    }
	
}
