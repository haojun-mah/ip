package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser for delete commands. */
public class DeleteCommandParser extends BaseCommandParser {

    public DeleteCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError =
                validateMinimumArgs(
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
