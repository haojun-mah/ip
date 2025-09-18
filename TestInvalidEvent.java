import audrey.task.List;

public class TestInvalidEvent {
    public static void main(String[] args) {
        List taskList = new List();
        
        try {
            String result = taskList.addEvent("invalid event");
            System.out.println("addEvent without /from /to: '" + result + "'");
        } catch (Exception e) {
            System.out.println("addEvent threw: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        
        try {
            String result = taskList.addEvent("task /from 2025-01-01");
            System.out.println("addEvent without /to: '" + result + "'");
        } catch (Exception e) {
            System.out.println("addEvent without /to threw: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        
        try {
            String result = taskList.addEvent("task /from invalid /to 2025-01-01");
            System.out.println("addEvent with invalid from date: '" + result + "'");
        } catch (Exception e) {
            System.out.println("addEvent with invalid from threw: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
