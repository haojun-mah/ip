import audrey.task.Event;
import audrey.exception.MissingEventException;

public class TestEventExtreme {
    public static void main(String[] args) {
        try {
            Event oldEvent = new Event("old /from 1900-01-01 /to 1900-01-02");
            System.out.println("Old event created successfully: " + oldEvent.toString());
        } catch (MissingEventException e) {
            System.out.println("Old event threw exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Old event threw other exception: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        
        try {
            Event futureEvent = new Event("future /from 3000-01-01 /to 3000-01-02");
            System.out.println("Future event created successfully: " + futureEvent.toString());
        } catch (MissingEventException e) {
            System.out.println("Future event threw exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Future event threw other exception: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
