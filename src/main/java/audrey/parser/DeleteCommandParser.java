package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser that handles {@code delete} commands and removes tasks by index. */
public class DeleteCommandParser extends BaseCommandParser {

    /**
     * Creates a parser dedicated to delete command processing.
     *
     * @param toDoList backing task list to modify
     * @param scanner  scanner supplying user input
     */
    public DeleteCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Executes the delete command by validating the task number and removing the task.
     *
     * @param processedInput tokenised user input containing the task index
     * @return user-facing result describing the outcome
     */
    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError = validateMinimumArgs(
                processedInput,
                "Delete requires a task number. Usage: delete [task number]");
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
            String deleteResult = toDoList.delete(taskNumber);
            print(deleteResult);
            return deleteResult;

        } catch (Exception e) {
            String errorMsg = "Error deleting task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
