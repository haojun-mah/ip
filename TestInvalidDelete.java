import audrey.task.List;

public class TestInvalidDelete {
    public static void main(String[] args) {
        List taskList = new List();
        taskList.addToDos("test task");
        
        try {
            String result = taskList.delete(999); // Out of bounds but positive
            System.out.println("delete(999): '" + result + "'");
        } catch (Exception e) {
            System.out.println("delete(999) threw: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        
        try {
            String result = taskList.delete(2); // Valid format but out of bounds
            System.out.println("delete(2): '" + result + "'");
        } catch (Exception e) {
            System.out.println("delete(2) threw: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
