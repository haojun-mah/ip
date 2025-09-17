package audrey.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import audrey.task.List;

/**
 * Encapsulate the command logic
 */
public class Storage {
    private final String filePath;
    private File db;
    private final List toDoList;

    /**
     * Constructor for Storage class.
     *
     * @param filePath Path to the storage file
     */
    public Storage(String filePath) {
        // Assert: File path should not be null or empty
        assert filePath != null : "File path cannot be null";
        assert !filePath.trim().isEmpty() : "File path cannot be empty";

        this.filePath = filePath;
        this.db = new File(filePath);
        this.toDoList = new List();

        // Assert: All components should be properly initialized
        assert this.filePath != null : "File path should be properly initialized";
        assert this.db != null : "File object should be properly initialized";
        assert this.toDoList != null : "Todo list should be properly initialized";

        if (db.exists()) {
            loadFromFile();
        } else {
            createNewFile();
        }
    }

    /**
     * Internal function which load db information into List
     */
    private void loadFromFile() {
        try (Scanner fileScanner = new Scanner(db)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    parseTaskLine(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Regex matching to store tasks into List
     *
     * @param line Task from db
     */
    private void parseTaskLine(String line) {
        try {
            if (isTodoLine(line)) {
                parseTodoLine(line);
            } else if (isDeadlineLine(line)) {
                parseDeadlineLine(line);
            } else if (isEventLine(line)) {
                parseEventLine(line);
            }
        } catch (Exception e) {
            System.out.println("Error parsing line: " + line + " - " + e.getMessage());
        }
    }

    private boolean isTodoLine(String line) {
        String todoPattern = "\\[T\\]\\[([X ])\\]\\s*(.+)";
        return line.matches(todoPattern);
    }

    private boolean isDeadlineLine(String line) {
        String deadlinePattern = "\\[D\\]\\[([X ])\\]\\s*(.+?)\\s*\\(by:\\s*(.+?)\\)";
        return line.matches(deadlinePattern);
    }

    private boolean isEventLine(String line) {
        String eventPattern = "\\[E\\]\\[([X ])\\]\\s*(.+?)\\s*\\(from:\\s*(.+?)\\s+to:\\s*(.+?)\\)";
        return line.matches(eventPattern);
    }

    private void parseTodoLine(String line) {
        String todoPattern = "\\[T\\]\\[([X ])\\]\\s*(.+)";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(todoPattern).matcher(line);
        if (matcher.find()) {
            String status = matcher.group(1);
            String task = matcher.group(2);
            toDoList.addToDos(task);
            if ("X".equals(status)) {
                toDoList.markTask(toDoList.size());
            }
        }
    }

    private void parseDeadlineLine(String line) {
        String deadlinePattern = "\\[D\\]\\[([X ])\\]\\s*(.+?)\\s*\\(by:\\s*(.+?)\\)";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(deadlinePattern).matcher(line);
        if (matcher.find()) {
            String status = matcher.group(1);
            String task = matcher.group(2);
            String deadline = matcher.group(3);
            toDoList.addDeadline(task + " /by " + deadline);
            if ("X".equals(status)) {
                toDoList.markTask(toDoList.size());
            }
        }
    }

    private void parseEventLine(String line) {
        String eventPattern = "\\[E\\]\\[([X ])\\]\\s*(.+?)\\s*\\(from:\\s*(.+?)\\s+to:\\s*(.+?)\\)";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(eventPattern).matcher(line);
        if (matcher.find()) {
            String status = matcher.group(1);
            String task = matcher.group(2);
            String from = matcher.group(3);
            String to = matcher.group(4);
            toDoList.addEvent(task + " /from " + from + " /to " + to);
            if ("X".equals(status)) {
                toDoList.markTask(toDoList.size());
            }
        }
    }

    /**
     * Creates a new DB
     */
    private void createNewFile() {
        try {
            db.createNewFile();
            System.out.println("Created new task file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    /**
     * Save info into DB
     */
    public void saveToFile() {
        // Assert: File path should be valid
        assert filePath != null && !filePath.trim().isEmpty() : "File path should be valid for saving";
        // Assert: Todo list should be initialized
        assert toDoList != null : "Todo list should be initialized for saving";

        try (FileWriter fw = new FileWriter(filePath)) {
            for (int i = 0; i < toDoList.size(); i++) {
                // Assert: Each task should exist and be valid
                assert toDoList.getTask(i) != null : "Task at index " + i + " should not be null";

                fw.write(toDoList.getTask(i).toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    /**
     * Returns list object containing tasks loaded from db.
     *
     * @return List object containing tasks
     */
    public List getToDoList() {
        return toDoList;
    }
}
