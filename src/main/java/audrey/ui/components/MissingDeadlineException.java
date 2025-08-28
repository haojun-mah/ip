package audrey.ui.components;

class MissingDeadlineException extends Exception{
    public MissingDeadlineException() {
        super("Missing Deadline Details!");
    }
}
