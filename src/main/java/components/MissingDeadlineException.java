package components;

class MissingDeadlineException extends Exception{
    public MissingDeadlineException() {
        super("Missing Deadline Details!");
    }
}
