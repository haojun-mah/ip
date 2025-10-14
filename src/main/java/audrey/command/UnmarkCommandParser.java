package audrey.command;

import java.util.Scanner;

import audrey.task.List;

/** Parser for unmark commands. */
public class UnmarkCommandParser extends BaseCommandParser {

    public UnmarkCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError =
                validateMinimumArgs(
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
