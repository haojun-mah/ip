import audrey.exception.MissingDeadlineException;
import audrey.exception.MissingEventException;
import audrey.exception.WrongFromToOrientationException;

public class TestExceptionMessages {
    public static void main(String[] args) {
        try {
            MissingDeadlineException mde = new MissingDeadlineException();
            System.out.println("MissingDeadlineException default: '" + mde.getMessage() + "'");
        } catch (Exception e) {
            System.out.println("MissingDeadlineException error: " + e.getMessage());
        }
        
        try {
            MissingEventException mee = new MissingEventException();
            System.out.println("MissingEventException default: '" + mee.getMessage() + "'");
        } catch (Exception e) {
            System.out.println("MissingEventException error: " + e.getMessage());
        }
        
        try {
            WrongFromToOrientationException wftoe = new WrongFromToOrientationException();
            System.out.println("WrongFromToOrientationException default: '" + wftoe.getMessage() + "'");
        } catch (Exception e) {
            System.out.println("WrongFromToOrientationException error: " + e.getMessage());
        }
    }
}
