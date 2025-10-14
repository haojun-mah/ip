package audrey.command;

import java.util.Scanner;

import audrey.task.List;

/** Parser for todo commands. */
public class TodoCommandParser extends BaseCommandParser {

    public TodoCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError =
                validateMinimumArgs(
                        processedInput,
                        "Todo description cannot be empty. Usage: todo [description]");
        if (validationError != null) {
            return validationError;
        }

        String description = processedInput[ARGS_INDEX].trim();

        // Validate and clean description
        String cleanedDescription = validateAndCleanDescription(description);
        if (cleanedDescription.isEmpty()) {
            String errorMsg = "Todo description cannot be empty after cleaning.";
            print(errorMsg);
            return errorMsg;
        }

        try {
            String todoResult = toDoList.addToDos(cleanedDescription);
            print(todoResult);
            return todoResult;

        } catch (Exception e) {
            String errorMsg = "Error adding todo: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
