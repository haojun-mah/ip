package audrey.exception;

public class MissingDeadlineException extends Exception {
    public MissingDeadlineException() {
        super("Missing Deadline Details!");
    }
}
