import audrey.storage.Storage;
import audrey.task.List;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestStorageLoad {
    public static void main(String[] args) throws IOException {
        String testFile = "/tmp/test_load.txt";
        
        // Create a file with the correct format (what Storage actually saves)
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("[T][ ] buy groceries\n");
            writer.write("[D][ ] submit report (by:2025-10-15)\n");
            writer.write("[E][ ] conference (from:2025-10-15 to:2025-10-16)\n");
        }
        
        // Load it with Storage
        Storage storage = new Storage(testFile);
        List todoList = storage.getToDoList();
        
        String listContent = todoList.showAllTasks();
        System.out.println("Loaded list content:");
        System.out.println("'" + listContent + "'");
        
        // Clean up
        Files.deleteIfExists(Paths.get(testFile));
    }
}
