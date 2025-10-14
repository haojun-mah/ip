package audrey.command;

import java.util.Scanner;

import audrey.task.List;

/** Parser for unsnooze commands. */
public class UnsnoozeCommandParser extends BaseCommandParser {

    public UnsnoozeCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError =
                validateMinimumArgs(
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
