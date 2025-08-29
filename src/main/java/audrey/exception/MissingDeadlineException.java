package audrey.exception;

/**
 * Exception created to represent missing deadline when creating deadline object.
 */
public class MissingDeadlineException extends Exception {
    public MissingDeadlineException() {
        super("Missing Deadline Details!");
    }
}
