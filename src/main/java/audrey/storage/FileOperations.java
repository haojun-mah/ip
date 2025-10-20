package audrey.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import audrey.task.List;

/** Handles file operations for storage. */
public class FileOperations extends BaseStorageOperation {

    private final String filePath;
    private final File db;

    /**
     * Builds a file operation helper tied to the task list and backing file.
     *
     * @param toDoList task list whose state is persisted
     * @param filePath path to the storage file on disk
     * @param db       handle to the storage file
     */
    public FileOperations(List toDoList, String filePath, File db) {
        super(toDoList);
        this.filePath = filePath;
        this.db = db;
    }

    /**
     * Unsupported for this class because file operations do not consume individual lines.
     *
     * @throws UnsupportedOperationException always thrown when invoked
     */
    @Override
    public void execute(String line) {
        // Not used for file operations
        throw new UnsupportedOperationException("FileOperations does not process lines");
    }

    /** Creates a new storage file if it doesn't exist. */
    public void createNewFile() {
        try {
            if (db.createNewFile()) {
                System.out.println("Created new database file: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error creating database file: " + e.getMessage());
        }
    }

    /** Saves the current task list to file. */
    public void saveToFile() {
        try {
            createBackup();

            try (FileWriter fw = new FileWriter(db)) {
                for (int i = 0; i < toDoList.size(); i++) {
                    audrey.task.Task task = toDoList.getTask(i);
                    if (task != null) {
                        fw.write(task.toString() + System.lineSeparator());
                    }
                }

                System.out.println("Tasks saved successfully to " + filePath);
            }

        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
            restoreFromBackup();
        } catch (Exception e) {
            System.err.println("Unexpected error during save: " + e.getMessage());
            restoreFromBackup();
        }
    }

    /** Creates a backup of the current file. */
    private void createBackup() {
        if (!db.exists()) {
            return;
        }

        File backupFile = new File(filePath + ".backup");

        try {
            java.nio.file.Files.copy(
                    db.toPath(),
                    backupFile.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Warning: Could not create backup file: " + e.getMessage());
        }
    }

    /** Restores from backup if save operation fails. */
    private void restoreFromBackup() {
        File backupFile = new File(filePath + ".backup");

        if (!backupFile.exists()) {
            System.err.println("No backup file found for restoration");
            return;
        }

        try {
            java.nio.file.Files.copy(
                    backupFile.toPath(),
                    db.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Database restored from backup");
        } catch (IOException e) {
            System.err.println("Critical error: Could not restore from backup: " + e.getMessage());
        }
    }
}
