package typecheck.model.adt;

public class MyException extends Exception {

    public MyException(String message) {
        super(message);
    }

    public String getMessage(){
        return super.getMessage();
    }
}
