import audrey.storage.Storage;
import audrey.task.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestStorageFormat {
    public static void main(String[] args) throws IOException {
        String testFile = "/tmp/test_storage.txt";
        Storage storage = new Storage(testFile);
        List todoList = storage.getToDoList();
        
        todoList.addToDos("test task");
        todoList.addDeadline("test deadline /by 2025-10-15");
        
        storage.saveToFile();
        
        String content = Files.readString(Paths.get(testFile));
        System.out.println("Actual file content:");
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
