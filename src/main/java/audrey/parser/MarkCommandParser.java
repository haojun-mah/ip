package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser that processes {@code mark} commands and marks tasks as completed. */
public class MarkCommandParser extends BaseCommandParser {

    /**
     * Creates a parser that marks tasks as done.
     *
     * @param toDoList backing task list to operate on
     * @param scanner  scanner supplying user input
     */
    public MarkCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Executes the mark command by validating the index and setting the task to done.
     *
     * @param processedInput tokenised user input containing the task index
     * @return user-facing result reflecting the updated task state
     */
    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError = validateMinimumArgs(
                processedInput, "Mark requires a task number. Usage: mark [task number]");
        if (validationError != null) {
            return validationError;
        }

        String numberStr = processedInput[ARGS_INDEX].trim();

        // Validate task number
        String taskValidationError = validateTaskNumber(numberStr);
        if (taskValidationError != null) {
            return taskValidationError;
        }

        try {
            int taskNumber = Integer.parseInt(numberStr);
            String markResult = toDoList.markTask(taskNumber);
            print(markResult);
            return markResult;

        } catch (Exception e) {
            String errorMsg = "Error marking task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
