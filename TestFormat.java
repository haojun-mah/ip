import audrey.task.List;
import audrey.task.Todo;

public class TestFormat {
    public static void main(String[] args) {
        List taskList = new List();
        Todo task = new Todo("test task");
        taskList.add(task);
        String result = taskList.showSnoozableTasks();
        System.out.println("Result: '" + result + "'");
        System.out.println("Lines:");
        String[] lines = result.split("\n");
        for (int i = 0; i < lines.length; i++) {
            System.out.println("Line " + i + ": '" + lines[i] + "'");
        }
    }
}
