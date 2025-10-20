package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser that marks tasks as incomplete when {@code unmark} is issued. */
public class UnmarkCommandParser extends BaseCommandParser {

    /**
     * Builds a parser that validates and unmarks tasks.
     *
     * @param toDoList backing task list to update
     * @param scanner  scanner providing raw user input
     */
    public UnmarkCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Parses and executes an unmark command, ensuring the task index is valid
     * before updating the task.
     *
     * @param processedInput tokenised user input containing the task index
     * @return outcome message from the task list or validation feedback
     */
    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError = validateMinimumArgs(
                processedInput,
                "Unmark requires a task number. Usage: unmark [task number]");
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
            String unmarkResult = toDoList.unmarkTask(taskNumber);
            print(unmarkResult);
            return unmarkResult;

        } catch (Exception e) {
            String errorMsg = "Error unmarking task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
