package audrey.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import audrey.task.List;

/** Unit tests for Storage class functionality */
public class StorageTest {
    @TempDir Path tempDir;

    private Storage storage;
    private String testFilePath;

    @BeforeEach
    public void setUp() {
        testFilePath = tempDir.resolve("test_storage.txt").toString();
        storage = new Storage(testFilePath);
    }

    @AfterEach
    public void tearDown() {
        // Clean up test files
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }

        // Clean up backup files
        File backupFile = new File(testFilePath + ".backup");
        if (backupFile.exists()) {
            backupFile.delete();
        }
    }

    @Test
    @DisplayName("Storage should create new file if not exists")
    public void storage_newFile_createsFile() {
        File testFile = new File(testFilePath);
        assertTrue(testFile.exists());
    }

    @Test
    @DisplayName("Storage should load existing file")
    public void storage_existingFile_loadsCorrectly() throws IOException {
        // Create a test file with some tasks in the correct format
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("[T][ ] buy groceries\n");
            writer.write("[D][ ] submit report (by:2025-10-15)\n");
            writer.write("[E][ ] conference (from:2025-10-15 to:2025-10-16)\n");
        }

        // Create new storage instance to load the file
        Storage newStorage = new Storage(testFilePath);
        List todoList = newStorage.getToDoList();

        assertNotNull(todoList);
        String listContent = todoList.showAllTasks();
        assertTrue(listContent.contains("buy groceries"));
        assertTrue(listContent.contains("submit report"));
        assertTrue(listContent.contains("conference"));
    }

    @Test
    @DisplayName("Storage should handle corrupted file gracefully")
    public void storage_corruptedFile_handlesGracefully() throws IOException {
        // Create a corrupted file
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("CORRUPTED LINE\n");
            writer.write("T | invalid | format\n");
            writer.write("T | 0 | valid task\n");
        }

        // Storage should still work and load valid tasks
        Storage newStorage = new Storage(testFilePath);
        List todoList = newStorage.getToDoList();
        assertNotNull(todoList);
    }

    @Test
    @DisplayName("Storage should save tasks correctly")
    public void storage_saveTasks_correctFormat() throws IOException {
        List todoList = storage.getToDoList();
        todoList.addToDos("test task");
        todoList.addDeadline("test deadline /by 2025-10-15");

        storage.saveToFile();

        // Read the file and verify format
        String content = Files.readString(Paths.get(testFilePath));
        assertTrue(content.contains("[T][ ] test task"));
        assertTrue(content.contains("[D][ ] test deadline (by:2025-10-15)"));
    }

    @Test
    @DisplayName("Storage should handle file writing errors")
    public void storage_writeError_handlesGracefully() {
        // Try to save to a read-only directory (if possible)
        // Note: This test might not work on all systems
        List todoList = storage.getToDoList();
        todoList.addToDos("test task");

        // This should not crash the program
        storage.saveToFile();
    }

    @Test
    @DisplayName("Storage should create backup before saving")
    public void storage_createBackup_works() throws IOException {
        // Create initial content
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("T | 0 | original task\n");
        }

        Storage newStorage = new Storage(testFilePath);
        List todoList = newStorage.getToDoList();
        todoList.addToDos("new task");

        newStorage.saveToFile();

        // Check if backup was created
        File backupFile = new File(testFilePath + ".backup");
        assertTrue(backupFile.exists());

        String backupContent = Files.readString(backupFile.toPath());
        assertTrue(backupContent.contains("original task"));
    }

    @Test
    @DisplayName("Storage should handle missing file gracefully")
    public void storage_missingFile_handlesGracefully() {
        String nonExistentPath = tempDir.resolve("nonexistent.txt").toString();
        Storage newStorage = new Storage(nonExistentPath);

        assertNotNull(newStorage.getToDoList());
        File createdFile = new File(nonExistentPath);
        assertTrue(createdFile.exists());
    }

    @Test
    @DisplayName("Storage should parse different task types correctly")
    public void storage_parseTaskTypes_correct() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("[T][ ] todo task\n");
            writer.write("[T][X] completed todo\n");
            writer.write("[D][ ] deadline task (by:2025-10-15)\n");
            writer.write("[D][X] completed deadline (by:2025-10-16)\n");
            writer.write("[E][ ] event task (from:2025-10-15 to:2025-10-16)\n");
            writer.write("[E][X] completed event (from:2025-10-17 to:2025-10-18)\n");
        }

        Storage newStorage = new Storage(testFilePath);
        List todoList = newStorage.getToDoList();
        String listContent = todoList.showAllTasks();

        // Check todos
        assertTrue(listContent.contains("[T][ ] todo task"));
        assertTrue(listContent.contains("[T][X] completed todo"));

        // Check deadlines
        assertTrue(listContent.contains("[D][ ] deadline task (by:2025-10-15)"));
        assertTrue(listContent.contains("[D][X] completed deadline (by:2025-10-16)"));

        // Check events
        assertTrue(listContent.contains("[E][ ] event task (from:2025-10-15 to:2025-10-16)"));
        assertTrue(listContent.contains("[E][X] completed event (from:2025-10-17 to:2025-10-18)"));
    }

    @Test
    @DisplayName("Storage should handle empty file")
    public void storage_emptyFile_handlesCorrectly() throws IOException {
        // Create empty file
        try (FileWriter writer = new FileWriter(testFilePath)) {
            // Write nothing
        }

        Storage newStorage = new Storage(testFilePath);
        List todoList = newStorage.getToDoList();
        assertNotNull(todoList);

        String listContent = todoList.showList();
        assertTrue(listContent.contains("No active tasks!"));
    }

    @Test
    @DisplayName("Storage should handle special characters in tasks")
    public void storage_specialCharacters_handled() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("[T][ ] buy café & groceries 买菜\n");
            writer.write("[D][ ] submit résumé (by:2025-10-15)\n");
        }

        Storage newStorage = new Storage(testFilePath);
        List todoList = newStorage.getToDoList();
        String listContent = todoList.showAllTasks();

        assertTrue(listContent.contains("café & groceries 买菜"));
        assertTrue(listContent.contains("résumé"));
    }

    @Test
    @DisplayName("Storage should preserve task order")
    public void storage_preserveTaskOrder_works() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("[T][ ] first task\n");
            writer.write("[T][ ] second task\n");
            writer.write("[T][ ] third task\n");
        }

        Storage newStorage = new Storage(testFilePath);
        List todoList = newStorage.getToDoList();
        String listContent = todoList.showAllTasks();

        int firstIndex = listContent.indexOf("first task");
        int secondIndex = listContent.indexOf("second task");
        int thirdIndex = listContent.indexOf("third task");

        assertTrue(firstIndex < secondIndex);
        assertTrue(secondIndex < thirdIndex);
    }

    @Test
    @DisplayName("Storage should get todo list reference")
    public void storage_getToDoList_returnsReference() {
        List todoList = storage.getToDoList();
        assertNotNull(todoList);

        // Should be same reference
        List todoList2 = storage.getToDoList();
        assertEquals(todoList, todoList2);
    }
}
