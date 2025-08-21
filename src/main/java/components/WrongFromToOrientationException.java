package components;

public class WrongFromToOrientationException extends Exception {
    public WrongFromToOrientationException() {
        super("To and From are in the wrong orientation");
    }
}
