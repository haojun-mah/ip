package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser that creates deadline tasks from {@code deadline} commands. */
public class DeadlineCommandParser extends BaseCommandParser {

    /**
     * Builds a parser capable of validating and adding deadline tasks.
     *
     * @param toDoList backing task list to update
     * @param scanner  scanner providing raw user input
     */
    public DeadlineCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Parses and executes a deadline command, ensuring both description and due date
     * are supplied before creating the task.
     *
     * @param processedInput tokenised user input containing description and `/by`
     * @return status message from the task list after attempting the addition
     */
    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError = validateMinimumArgs(
                processedInput,
                "Deadline description cannot be empty. Usage: deadline [description] /by [date]");
        if (validationError != null) {
            return validationError;
        }

        String fullDescription = processedInput[ARGS_INDEX].trim();

        // Validate /by format
        if (!fullDescription.contains("/by")) {
            String errorMsg = "Deadline must include '/by [date]'. "
                    + "Usage: deadline [description] /by [YYYY-MM-DD]";
            print(errorMsg);
            return errorMsg;
        }

        // Check for multiple /by occurrences
        if (fullDescription.split("/by").length > 2) {
            String errorMsg = "Multiple '/by' found. Please use '/by' only once.";
            print(errorMsg);
            return errorMsg;
        }

        String[] parts = fullDescription.split("/by", 2);
        if (parts.length != 2) {
            String errorMsg = "Invalid deadline format. " + "Usage: deadline [description] /by [YYYY-MM-DD]";
            print(errorMsg);
            return errorMsg;
        }

        String description = parts[0].trim();
        String dateStr = parts[1].trim();

        if (description.isEmpty()) {
            String errorMsg = "Deadline description cannot be empty.";
            print(errorMsg);
            return errorMsg;
        }

        if (dateStr.isEmpty()) {
            String errorMsg = "Date cannot be empty. Use format: YYYY-MM-DD";
            print(errorMsg);
            return errorMsg;
        }

        // Validate date format
        if (!dateStr.matches(DATE_PATTERN)) {
            String errorMsg = "Invalid date format: '" + dateStr + "'. Use YYYY-MM-DD format.";
            print(errorMsg);
            return errorMsg;
        }

        // Clean description
        String cleanedDescription = validateAndCleanDescription(description);
        if (cleanedDescription.isEmpty()) {
            String errorMsg = "Deadline description cannot be empty after cleaning.";
            print(errorMsg);
            return errorMsg;
        }

        try {
            String deadlineResult = toDoList.addDeadline(cleanedDescription + " /by " + dateStr);
            print(deadlineResult);
            return deadlineResult;

        } catch (Exception e) {
            String errorMsg = "Error adding deadline: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
