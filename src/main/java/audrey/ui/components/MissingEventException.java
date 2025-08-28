package audrey.ui.components;

class MissingEventException extends Exception{
    public MissingEventException() {
        super("Missing Event Details!");
    }
}