package audrey.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import audrey.task.List;

/** Encapsulate the command logic */
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

    /** Internal function which load db information into List */
    private void loadFromFile() {
        try {
            // Validate file exists and is readable
            if (!db.exists()) {
                System.out.println("Warning: Storage file does not exist: " + filePath);
                return;
            }

            if (!db.canRead()) {
                System.out.println(
                        "Error: Cannot read storage file. Check file permissions: " + filePath);
                return;
            }

            if (db.length() == 0) {
                System.out.println("Info: Storage file is empty: " + filePath);
                return;
            }

            try (Scanner fileScanner = new Scanner(db)) {
                int lineNumber = 0;
                int successfullyParsed = 0;
                int failedToParse = 0;

                while (fileScanner.hasNextLine()) {
                    lineNumber++;
                    String line = fileScanner.nextLine();

                    // Skip empty lines
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    // Validate line length
                    if (line.length() > 500) {
                        System.out.println(
                                "Warning: Line "
                                        + lineNumber
                                        + " is too long, skipping: "
                                        + line.substring(0, 50)
                                        + "...");
                        failedToParse++;
                        continue;
                    }

                    try {
                        parseTaskLine(line);
                        successfullyParsed++;
                    } catch (Exception e) {
                        System.out.println(
                                "Error parsing line "
                                        + lineNumber
                                        + ": "
                                        + line
                                        + " - "
                                        + e.getMessage());
                        failedToParse++;
                    }
                }

                System.out.println("Loaded " + successfullyParsed + " tasks from storage.");
                if (failedToParse > 0) {
                    System.out.println("Warning: " + failedToParse + " lines could not be parsed.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.out.println("Starting with empty task list.");
        } catch (Exception e) {
            System.out.println("Unexpected error loading file: " + e.getMessage());
            System.out.println("Starting with empty task list.");
        }
    }

    /**
     * Regex matching to store tasks into List
     *
     * @param line Task from db
     */
    private void parseTaskLine(String line) {
        try {
            // Validate input
            if (line == null || line.trim().isEmpty()) {
                throw new IllegalArgumentException("Line cannot be null or empty");
            }

            String trimmedLine = line.trim();

            // Check for basic task format
            if (!trimmedLine.matches("\\[([TDE])\\]\\[([X ])\\].*")) {
                throw new IllegalArgumentException("Invalid task format: " + trimmedLine);
            }

            if (isTodoLine(trimmedLine)) {
                parseTodoLine(trimmedLine);
            } else if (isDeadlineLine(trimmedLine)) {
                parseDeadlineLine(trimmedLine);
            } else if (isEventLine(trimmedLine)) {
                parseEventLine(trimmedLine);
            } else {
                throw new IllegalArgumentException("Unknown task type: " + trimmedLine);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error parsing task line: " + line + " - " + e.getMessage(), e);
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
        String eventPattern =
                "\\[E\\]\\[([X ])\\]\\s*(.+?)\\s*\\(from:\\s*(.+?)\\s+to:\\s*(.+?)\\)";
        return line.matches(eventPattern);
    }

    private void parseTodoLine(String line) {
        String todoPattern = "\\[T\\]\\[([X ])\\]\\s*(.+)";
        java.util.regex.Matcher matcher =
                java.util.regex.Pattern.compile(todoPattern).matcher(line);
        if (matcher.find()) {
            String status = matcher.group(1);
            String task = matcher.group(2);

            // Validate task content
            if (task == null || task.trim().isEmpty()) {
                throw new IllegalArgumentException("Todo task description cannot be empty");
            }

            if (task.length() > 200) {
                throw new IllegalArgumentException(
                        "Todo task description too long: " + task.length() + " characters");
            }

            // Validate status
            if (status == null || (!status.equals("X") && !status.equals(" "))) {
                throw new IllegalArgumentException("Invalid todo status: '" + status + "'");
            }

            try {
                toDoList.addToDos(task.trim());
                if ("X".equals(status)) {
                    toDoList.markTask(toDoList.size());
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to add todo task: " + task, e);
            }
        } else {
            throw new IllegalArgumentException(
                    "Todo line does not match expected pattern: " + line);
        }
    }

    private void parseDeadlineLine(String line) {
        String deadlinePattern = "\\[D\\]\\[([X ])\\]\\s*(.+?)\\s*\\(by:\\s*(.+?)\\)";
        java.util.regex.Matcher matcher =
                java.util.regex.Pattern.compile(deadlinePattern).matcher(line);
        if (matcher.find()) {
            String status = matcher.group(1);
            String task = matcher.group(2);
            String deadline = matcher.group(3);

            // Validate components
            if (task == null || task.trim().isEmpty()) {
                throw new IllegalArgumentException("Deadline task description cannot be empty");
            }

            if (deadline == null || deadline.trim().isEmpty()) {
                throw new IllegalArgumentException("Deadline date cannot be empty");
            }

            if (task.length() > 200) {
                throw new IllegalArgumentException(
                        "Deadline task description too long: " + task.length() + " characters");
            }

            // Validate status
            if (status == null || (!status.equals("X") && !status.equals(" "))) {
                throw new IllegalArgumentException("Invalid deadline status: '" + status + "'");
            }

            // Basic date format validation
            String trimmedDeadline = deadline.trim();
            if (!trimmedDeadline.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println(
                        "Warning: Deadline date may not be in expected format (YYYY-MM-DD): "
                                + trimmedDeadline);
            }

            try {
                toDoList.addDeadline(task.trim() + " /by " + trimmedDeadline);
                if ("X".equals(status)) {
                    toDoList.markTask(toDoList.size());
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to add deadline task: " + task, e);
            }
        } else {
            throw new IllegalArgumentException(
                    "Deadline line does not match expected pattern: " + line);
        }
    }

    private void parseEventLine(String line) {
        String eventPattern =
                "\\[E\\]\\[([X ])\\]\\s*(.+?)\\s*\\(from:\\s*(.+?)\\s+to:\\s*(.+?)\\)";
        java.util.regex.Matcher matcher =
                java.util.regex.Pattern.compile(eventPattern).matcher(line);
        if (matcher.find()) {
            String status = matcher.group(1);
            String task = matcher.group(2);
            String from = matcher.group(3);
            String to = matcher.group(4);

            // Validate components
            if (task == null || task.trim().isEmpty()) {
                throw new IllegalArgumentException("Event task description cannot be empty");
            }

            if (from == null || from.trim().isEmpty()) {
                throw new IllegalArgumentException("Event 'from' date cannot be empty");
            }

            if (to == null || to.trim().isEmpty()) {
                throw new IllegalArgumentException("Event 'to' date cannot be empty");
            }

            if (task.length() > 200) {
                throw new IllegalArgumentException(
                        "Event task description too long: " + task.length() + " characters");
            }

            // Validate status
            if (status == null || (!status.equals("X") && !status.equals(" "))) {
                throw new IllegalArgumentException("Invalid event status: '" + status + "'");
            }

            // Basic date format validation
            String trimmedFrom = from.trim();
            String trimmedTo = to.trim();

            if (!trimmedFrom.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println(
                        "Warning: Event 'from' date may not be in expected format (YYYY-MM-DD): "
                                + trimmedFrom);
            }

            if (!trimmedTo.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println(
                        "Warning: Event 'to' date may not be in expected format (YYYY-MM-DD): "
                                + trimmedTo);
            }

            // Check logical date order (basic string comparison for YYYY-MM-DD format)
            if (trimmedFrom.compareTo(trimmedTo) > 0) {
                System.out.println(
                        "Warning: Event 'from' date is after 'to' date: "
                                + trimmedFrom
                                + " > "
                                + trimmedTo);
            }

            try {
                toDoList.addEvent(task.trim() + " /from " + trimmedFrom + " /to " + trimmedTo);
                if ("X".equals(status)) {
                    toDoList.markTask(toDoList.size());
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to add event task: " + task, e);
            }
        } else {
            throw new IllegalArgumentException(
                    "Event line does not match expected pattern: " + line);
        }
    }

    /** Creates a new DB */
    private void createNewFile() {
        try {
            db.createNewFile();
            System.out.println("Created new task file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    /** Save info into DB with comprehensive error handling */
    public void saveToFile() {
        // Assert: File path should be valid
        assert filePath != null && !filePath.trim().isEmpty()
                : "File path should be valid for saving";
        // Assert: Todo list should be initialized
        assert toDoList != null : "Todo list should be initialized for saving";

        try {
            // Check if we can write to the directory
            File parentDir = new File(filePath).getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new IOException("Cannot create parent directories for: " + filePath);
                }
            }

            // Check if file exists and we can write to it
            File file = new File(filePath);
            if (file.exists() && !file.canWrite()) {
                throw new IOException("Cannot write to file (permission denied): " + filePath);
            }

            // Create backup if file already exists
            if (file.exists() && file.length() > 0) {
                createBackup();
            }

            try (FileWriter fw = new FileWriter(filePath)) {
                int savedTasks = 0;
                for (int i = 0; i < toDoList.size(); i++) {
                    // Assert: Each task should exist and be valid
                    assert toDoList.getTask(i) != null
                            : "Task at index " + i + " should not be null";

                    String taskString = toDoList.getTask(i).toString();

                    // Validate task string before writing
                    if (taskString == null || taskString.trim().isEmpty()) {
                        System.out.println("Warning: Skipping empty task at index " + i);
                        continue;
                    }

                    if (taskString.length() > 500) {
                        System.out.println(
                                "Warning: Task at index "
                                        + i
                                        + " is very long ("
                                        + taskString.length()
                                        + " chars)");
                    }

                    fw.write(taskString + "\n");
                    savedTasks++;
                }

                fw.flush(); // Ensure data is written to disk
                System.out.println("Successfully saved " + savedTasks + " tasks to " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
            System.err.println("Your tasks may not be saved properly!");

            // Try to restore from backup if save failed
            restoreFromBackup();
        } catch (Exception e) {
            System.err.println("Unexpected error saving to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Creates a backup of the current storage file */
    private void createBackup() {
        try {
            File original = new File(filePath);
            File backup = new File(filePath + ".backup");

            if (original.exists()) {
                try (Scanner scanner = new Scanner(original);
                        FileWriter writer = new FileWriter(backup)) {
                    while (scanner.hasNextLine()) {
                        writer.write(scanner.nextLine() + "\n");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not create backup file: " + e.getMessage());
        }
    }

    /** Restores from backup if main file is corrupted */
    private void restoreFromBackup() {
        try {
            File backup = new File(filePath + ".backup");
            File original = new File(filePath);

            if (backup.exists()) {
                try (Scanner scanner = new Scanner(backup);
                        FileWriter writer = new FileWriter(original)) {
                    while (scanner.hasNextLine()) {
                        writer.write(scanner.nextLine() + "\n");
                    }
                }
                System.out.println("Restored tasks from backup file.");
            }
        } catch (IOException e) {
            System.err.println("Could not restore from backup: " + e.getMessage());
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
