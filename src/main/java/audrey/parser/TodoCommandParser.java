package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser that creates todo tasks from {@code todo} commands. */
public class TodoCommandParser extends BaseCommandParser {

    /**
     * Builds a parser that validates and adds todo tasks.
     *
     * @param toDoList backing task list to update
     * @param scanner  scanner providing raw user input
     */
    public TodoCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Parses and executes a todo command after ensuring the description is
     * present.
     *
     * @param processedInput tokenised user input containing the description
     * @return status message from the task list after attempting the addition
     */
    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError = validateMinimumArgs(
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
