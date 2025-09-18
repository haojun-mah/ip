import audrey.task.List;
import audrey.task.Todo;

public class TestFormat2 {
    public static void main(String[] args) {
        List taskList = new List();
        taskList.addToDos("test task");
        String result = taskList.showSnoozableTasks();
        System.out.println("Result:");
        System.out.println("'" + result + "'");
        
        // Test specific line
        String[] lines = result.split("\n");
        for (int i = 0; i < lines.length; i++) {
            System.out.println("Line " + i + ": '" + lines[i] + "'");
        }
    }
}
