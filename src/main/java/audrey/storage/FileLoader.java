package audrey.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import audrey.task.List;

/** Handles loading tasks from storage files. */
public class FileLoader extends BaseStorageOperation {

    private final File db;
    private final TaskLineProcessor lineProcessor;

    /**
     * Builds a loader that hydrates the task list from the backing file.
     *
     * @param toDoList task list to populate
     * @param db       handle to the storage file
     */
    public FileLoader(List toDoList, File db) {
        super(toDoList);
        this.db = db;
        this.lineProcessor = new TaskLineProcessor(toDoList);
    }

    /**
     * Unsupported for this class because loading operates on entire files instead of single lines.
     *
     * @throws UnsupportedOperationException always thrown when invoked
     */
    @Override
    public void execute(String line) {
        // Not used for file loading
        throw new UnsupportedOperationException("FileLoader does not process individual lines");
    }

    /** Loads all tasks from the storage file. */
    public void loadFromFile() {
        // Assert: File should exist before loading
        assert db != null && db.exists() : "Database file should exist before loading";

        try (Scanner sc = new Scanner(db)) {
            int linesProcessed = 0;
            int tasksLoaded = 0;
            int initialTaskCount = toDoList.size();

            while (sc.hasNext()) {
                String line = sc.nextLine();
                linesProcessed++;

                try {
                    lineProcessor.execute(line);
                    // Check if a task was actually added
                    if (toDoList.size() > initialTaskCount + tasksLoaded) {
                        tasksLoaded++;
                    }
                } catch (Exception e) {
                    System.err.println(
                            "Error processing line " + linesProcessed + ": " + e.getMessage());
                }
            }

            System.out.println(
                    "Database loaded: "
                            + tasksLoaded
                            + " tasks loaded from "
                            + linesProcessed
                            + " lines");

            // Assert: At least some data should be loaded from existing file
            if (linesProcessed > 0 && tasksLoaded == 0) {
                System.out.println("Warning: File contained data but no tasks were loaded");
            }

        } catch (FileNotFoundException e) {
            System.err.println("Database file not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error reading database file: " + e.getMessage());
        }
    }
}
