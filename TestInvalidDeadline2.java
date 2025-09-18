import audrey.task.List;

public class TestInvalidDeadline2 {
    public static void main(String[] args) {
        List taskList = new List();
        
        try {
            String result = taskList.addDeadline("test task /by invalid-date");
            System.out.println("addDeadline with invalid date: '" + result + "'");
        } catch (Exception e) {
            System.out.println("addDeadline threw: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        
        try {
            String result = taskList.addDeadline("test task without deadline");
            System.out.println("addDeadline without /by: '" + result + "'");
        } catch (Exception e) {
            System.out.println("addDeadline without /by threw: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
