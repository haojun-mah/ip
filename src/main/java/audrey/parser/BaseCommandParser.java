package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/**
 * Abstract base class for command parsers. Provides common functionality and constants for all
 * command parsing operations.
 */
public abstract class BaseCommandParser {
    // Constants for validation limits
    protected static final int MAX_WHITESPACE_EXCESS = 10;
    protected static final int MAX_COMMAND_LENGTH = 1000;
    protected static final int MAX_DESCRIPTION_LENGTH = 200;

    // Constants for formatting
    protected static final String INDENT = "    ";
    protected static final String SEPARATOR_LINE =
            "    ____________________________________________________________________";

    // Constants for date format
    protected static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    // Constants for array operations
    protected static final int SPLIT_LIMIT_TWO = 2;
    protected static final int COMMAND_INDEX = 0;
    protected static final int ARGS_INDEX = 1;
    protected static final int MIN_ARGS_LENGTH = 2;

    protected final List toDoList;
    protected final Scanner scanner;

    /**
     * Constructor for BaseCommandParser.
     *
     * @param toDoList The task list to operate on
     * @param scanner The scanner for input operations
     */
    public BaseCommandParser(List toDoList, Scanner scanner) {
        assert toDoList != null : "Todo list cannot be null";
        assert scanner != null : "Scanner cannot be null";

        this.toDoList = toDoList;
        this.scanner = scanner;
    }

    /**
     * Abstract method to be implemented by concrete command parsers.
     *
     * @param processedInput The processed input array [command, args]
     * @return The result message from executing the command
     */
    public abstract String execute(String[] processedInput);

    /**
     * Validates that the processed input has the minimum required arguments.
     *
     * @param processedInput The input to validate
     * @param errorMessage The error message to return if validation fails
     * @return Error message if validation fails, null if successful
     */
    protected String validateMinimumArgs(String[] processedInput, String errorMessage) {
        if (processedInput.length < MIN_ARGS_LENGTH
                || processedInput[ARGS_INDEX].trim().isEmpty()) {
            print(errorMessage);
            return errorMessage;
        }
        return null;
    }

    /**
     * Validates a task number string.
     *
     * @param numberStr The task number string to validate
     * @return Error message if validation fails, null if successful
     */
    protected String validateTaskNumber(String numberStr) {
        try {
            int taskNumber = Integer.parseInt(numberStr);
            if (taskNumber <= 0) {
                String errorMsg = "Task number must be a positive integer.";
                print(errorMsg);
                return errorMsg;
            }
            if (taskNumber > toDoList.size()) {
                String errorMsg =
                        "Task number "
                                + taskNumber
                                + " does not exist. You have "
                                + toDoList.size()
                                + " tasks.";
                print(errorMsg);
                return errorMsg;
            }
            return null;
        } catch (NumberFormatException e) {
            String errorMsg =
                    "Invalid task number: '" + numberStr + "'. Please enter a valid number.";
            print(errorMsg);
            return errorMsg;
        }
    }

    /**
     * Validates and filters task description for invalid characters.
     *
     * @param description The description to validate
     * @return The cleaned description
     */
    protected String validateAndCleanDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return "";
        }

        String cleaned = description.replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F]", "");

        if (cleaned.length() > MAX_DESCRIPTION_LENGTH) {
            print("Warning: Description truncated to " + MAX_DESCRIPTION_LENGTH + " characters.");
            cleaned = cleaned.substring(0, MAX_DESCRIPTION_LENGTH);
        }

        return cleaned.trim();
    }

    /**
     * Prints a message with proper formatting.
     *
     * @param message The message to print
     */
    protected void print(String message) {
        System.out.println(SEPARATOR_LINE);
        System.out.println(INDENT + message);
        System.out.println(SEPARATOR_LINE);
    }
}
