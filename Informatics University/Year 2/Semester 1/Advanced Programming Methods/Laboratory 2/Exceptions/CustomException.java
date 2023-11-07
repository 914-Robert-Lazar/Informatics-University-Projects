package Exceptions;

public class CustomException extends Exception {
    public CustomException(String errorMessage)
    {
        super(errorMessage);
    }

    public void printMessage()
    {
        System.out.println(this.getMessage());
    }
}
