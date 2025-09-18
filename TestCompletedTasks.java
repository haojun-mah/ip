import audrey.storage.Storage;
import audrey.task.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestCompletedTasks {
    public static void main(String[] args) throws IOException {
        String testFile = "/tmp/test_completed.txt";
        Storage storage = new Storage(testFile);
        List todoList = storage.getToDoList();
        
        todoList.addToDos("test task");
        todoList.addDeadline("test deadline /by 2025-10-15");
        todoList.addEvent("test event /from 2025-10-15 /to 2025-10-16");
        
        // Mark tasks as completed
        todoList.markTask(1);  // Mark first task (todo)
        todoList.markTask(3);  // Mark third task (event)
        
        storage.saveToFile();
        
        String content = Files.readString(Paths.get(testFile));
        System.out.println("Saved completed tasks:");
        System.out.println("'" + content + "'");
        System.out.println("---");
        
        String[] lines = content.split("\n");
        for (int i = 0; i < lines.length; i++) {
            System.out.println("Line " + i + ": '" + lines[i] + "'");
        }
        
        // Clean up
        Files.deleteIfExists(Paths.get(testFile));
    }
}
