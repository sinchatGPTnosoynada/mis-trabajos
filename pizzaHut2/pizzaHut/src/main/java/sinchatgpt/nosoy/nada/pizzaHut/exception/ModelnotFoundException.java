package sinchatgpt.nosoy.nada.pizzaHut.exception;

public class ModelnotFoundException extends RuntimeException {

    private final int errorCode;

    public ModelnotFoundException(String message){

        super(message);

        this.errorCode = 0;

    }

    public ModelnotFoundException(String message, int errorCode){

        super(message);

        this.errorCode = errorCode;

    }

}
