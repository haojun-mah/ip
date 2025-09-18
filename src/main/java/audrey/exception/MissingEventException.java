package audrey.exception;

/** Exception created to represent missing event from or to information when creating event. */
public class MissingEventException extends Exception {
    public MissingEventException() {
        super("Missing Event Details!");
    }
}
