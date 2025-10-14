package audrey.command;

import audrey.task.List;

/**
 * Abstract base class for all command implementations. Each specific command should extend this
 * class and implement the execute method.
 */
public abstract class BaseCommand {
    // Constants for formatting (shared across command classes)
    protected static final String INDENT = "    ";
    protected static final String SEPARATOR_LINE =
            "    ____________________________________________________________________";

    // Constants for validation
    protected static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    protected static final int MAX_DESCRIPTION_LENGTH = 200;
    protected static final int MIN_ARGS_LENGTH = 2;
    protected static final int ARGS_INDEX = 1;

    protected final List toDoList;

    /**
     * Constructor for base command.
     *
     * @param toDoList The task list to operate on
     */
    public BaseCommand(List toDoList) {
        assert toDoList != null : "Todo list cannot be null";
        this.toDoList = toDoList;
    }

    /**
     * Executes the command with the given input.
     *
     * @param processedInput Array containing command and arguments
     * @return Result message from command execution
     */
    public abstract String execute(String[] processedInput);

    /**
     * Prettier print for CLI.
     *
     * @param string Text to print
     */
    protected void print(String string) {
        String[] splitString = string.split("\n");
        StringBuilder formattedString = new StringBuilder();
        for (int i = 0; i < splitString.length; i++) {
            if (i + 1 == splitString.length) {
                formattedString.append(INDENT).append(splitString[i]);
            } else {
                formattedString.append(INDENT).append(splitString[i]).append('\n');
            }
        }
        System.out.println(SEPARATOR_LINE);
        System.out.println(formattedString.toString());
        System.out.println(SEPARATOR_LINE);
    }

    /**
     * Validates that required arguments are present.
     *
     * @param processedInput Command input array
     * @param usage Usage message to display on error
     * @return Error message if validation fails, null if successful
     */
    protected String validateMinimumArgs(String[] processedInput, String usage) {
        if (processedInput.length < MIN_ARGS_LENGTH
                || processedInput[ARGS_INDEX].trim().isEmpty()) {
            String errorMsg = usage;
            print(errorMsg);
            return errorMsg;
        }
        return null;
    }

    /**
     * Validates task number format and range.
     *
     * @param taskNumberStr String representation of task number
     * @return Error message if validation fails, null if successful
     */
    protected String validateTaskNumber(String taskNumberStr) {
        if (!taskNumberStr.matches("\\d+")) {
            String errorMsg =
                    "Invalid task number format: '"
                            + taskNumberStr
                            + "'. Please enter a positive integer.";
            print(errorMsg);
            return errorMsg;
        }

        int taskNumber = Integer.parseInt(taskNumberStr);
        if (taskNumber <= 0) {
            String errorMsg = "Task number must be positive. You entered: " + taskNumber;
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
    }
}
