package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser that resumes snoozed tasks when {@code unsnooze} is issued. */
public class UnsnoozeCommandParser extends BaseCommandParser {

    /**
     * Builds a parser that validates and unsnoozes tasks.
     *
     * @param toDoList backing task list to update
     * @param scanner  scanner providing raw user input
     */
    public UnsnoozeCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Parses and executes an unsnooze command, reactivating the specified task if
     * the index is valid.
     *
     * @param processedInput tokenised user input containing the task index
     * @return task list message describing the result
     */
    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError = validateMinimumArgs(
                processedInput,
                "Unsnooze requires a task number. Usage: unsnooze [task number]");
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
            String unsnoozeResult = toDoList.unsnoozeTask(taskNumber);
            print(unsnoozeResult);
            return unsnoozeResult;

        } catch (Exception e) {
            String errorMsg = "Error unsnoozing task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
