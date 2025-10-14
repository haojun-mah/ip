package audrey.command;

import java.util.Scanner;

import audrey.task.List;

/** Parser for mark commands. */
public class MarkCommandParser extends BaseCommandParser {

    public MarkCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError =
                validateMinimumArgs(
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
