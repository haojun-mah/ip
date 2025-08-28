package audrey.exception;

/**
 * Exception created to represent to and from key in the wrong orientation
 * e.g. /to 123 /from 1234
 */
public class WrongFromToOrientationException extends Exception {
    public WrongFromToOrientationException() {
        super("To and From are in the wrong orientation");
    }
}
