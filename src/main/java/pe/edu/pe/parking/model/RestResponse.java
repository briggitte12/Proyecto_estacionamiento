package pe.edu.pe.parking.model;

import java.io.Serializable;

public class RestResponse implements Serializable {
    public enum STATUS {SUCCESS, FAIL}

    private STATUS status;
    private String message;

    public RestResponse(STATUS status, String message) {
        this.status = status;
        this.message = message;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
