import audrey.task.List;

public class TestInvalidDeadline {
    public static void main(String[] args) {
        List taskList = new List();
        
        try {
            String result = taskList.addDeadline("test task", "invalid-date");
            System.out.println("addDeadline with invalid date: '" + result + "'");
        } catch (Exception e) {
            System.out.println("addDeadline threw: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
