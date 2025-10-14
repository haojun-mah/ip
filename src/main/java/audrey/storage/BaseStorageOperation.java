package audrey.storage;

import audrey.task.List;

/**
 * Abstract base class for storage operations. Provides common functionality and constants for all
 * storage operations.
 */
public abstract class BaseStorageOperation {
    // Constants for validation limits
    protected static final int MAX_LINE_LENGTH = 500;
    protected static final int MAX_DESCRIPTION_LENGTH = 200;

    // Constants for date format
    protected static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    // Task pattern constants
    protected static final String TODO_PATTERN = "\\[T\\]\\[([X ])\\]\\s*(.+)";
    protected static final String DEADLINE_PATTERN =
            "\\[D\\]\\[([X ])\\]\\s*(.+?)\\s*\\(by:\\s*(.+?)\\)";
    protected static final String EVENT_PATTERN =
            "\\[E\\]\\[([X ])\\]\\s*(.+?)\\s*\\(from:\\s*(.+?)\\s+to:\\s*(.+?)\\)";

    protected final List toDoList;

    /**
     * Constructor for BaseStorageOperation.
     *
     * @param toDoList The task list to operate on
     */
    public BaseStorageOperation(List toDoList) {
        assert toDoList != null : "Todo list cannot be null";
        this.toDoList = toDoList;
    }

    /**
     * Abstract method to be implemented by concrete storage operations.
     *
     * @param line The line to process
     */
    public abstract void execute(String line);

    /**
     * Validates task description length and content.
     *
     * @param description The task description to validate
     * @param taskType The type of task for error messaging
     */
    protected void validateTaskDescription(String description, String taskType) {
        assert description != null : "Description cannot be null";
        assert taskType != null : "Task type cannot be null";

        String trimmedDescription = description.trim();
        if (trimmedDescription.isEmpty()) {
            throw new IllegalArgumentException(taskType + " task description cannot be empty");
        }

        if (trimmedDescription.length() > MAX_DESCRIPTION_LENGTH) {
            System.out.println(
                    "Warning: "
                            + taskType
                            + " description is quite long: "
                            + trimmedDescription.length()
                            + " characters");
        }
    }

    /**
     * Validates task status format.
     *
     * @param status The status to validate (should be "X" or " ")
     * @param taskType The type of task for error messaging
     */
    protected void validateTaskStatus(String status, String taskType) {
        assert status != null : "Status cannot be null";
        assert taskType != null : "Task type cannot be null";

        if (!status.equals("X") && !status.equals(" ")) {
            throw new IllegalArgumentException(
                    "Invalid " + taskType + " status: " + status + ". Expected 'X' or ' '");
        }
    }

    /**
     * Validates date format.
     *
     * @param date The date to validate
     * @param dateType The type of date for error messaging
     */
    protected void validateDateFormat(String date, String dateType) {
        assert date != null : "Date cannot be null";
        assert dateType != null : "Date type cannot be null";

        String trimmedDate = date.trim();
        if (!trimmedDate.matches(DATE_PATTERN)) {
            System.out.println(
                    "Warning: "
                            + dateType
                            + " date may not be in expected format (YYYY-MM-DD): "
                            + trimmedDate);
        }
    }
}
