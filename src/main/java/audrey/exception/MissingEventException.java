package audrey.exception;

public class MissingEventException extends Exception{
    public MissingEventException() {
        super("Missing Event Details!");
    }
}