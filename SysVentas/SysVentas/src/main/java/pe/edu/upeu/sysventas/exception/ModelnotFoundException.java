package pe.edu.upeu.sysventas.exception;

import lombok.Getter;
import org.springframework.boot.Banner;

public class ModelnotFoundException extends RuntimeException {

    private final int errorCode;

    public ModelnotFoundException (String message){

        super(message);

        this.errorCode = 0;

    }

    public ModelnotFoundException (String message, int errorCode){

        super(message);

        this.errorCode = errorCode;

    }

}
