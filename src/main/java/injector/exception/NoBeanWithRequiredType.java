package injector.exception;

public class NoBeanWithRequiredType extends Exception{

    public NoBeanWithRequiredType(String message){
        super(message);
    }

}
