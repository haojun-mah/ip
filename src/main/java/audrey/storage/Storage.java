package audrey.storage;

import java.io.File;

import audrey.task.List;

/** Encapsulates storage operations using composition of specialized storage classes. */
public class Storage {
    private final String filePath;
    private final File db;
    private final List toDoList;
    private final FileLoader fileLoader;
    private final FileOperations fileOperations;

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
        this.fileLoader = new FileLoader(toDoList, db);
        this.fileOperations = new FileOperations(toDoList, filePath, db);

        // Assert: All components should be properly initialized
        assert this.filePath != null : "File path should be properly initialized";
        assert this.db != null : "File object should be properly initialized";
        assert this.toDoList != null : "Todo list should be properly initialized";

        if (db.exists()) {
            fileLoader.loadFromFile();
        } else {
            fileOperations.createNewFile();
        }
    }

    /** Persists the current task list to disk using {@link FileOperations}. */
    public void saveToFile() {
        fileOperations.saveToFile();
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
