import audrey.task.List;

public class TestInvalidSnooze {
    public static void main(String[] args) {
        List taskList = new List();
        try {
            String result = taskList.snoozeTaskForever(1); // No tasks added
            System.out.println("Result: '" + result + "'");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getClass().getSimpleName());
            System.out.println("Message: '" + e.getMessage() + "'");
            e.printStackTrace();
        }
    }
}
