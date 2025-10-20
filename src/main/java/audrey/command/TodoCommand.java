package audrey.command;

import audrey.task.List;

/** Command that adds a todo task after validating its description. */
public class TodoCommand extends BaseCommand {

    /**
     * Builds a command that validates and adds todo tasks.
     *
     * @param toDoList backing task list to update
     */
    public TodoCommand(List toDoList) {
        super(toDoList);
    }

    /**
     * Ensures the description is present and free of invalid characters before adding
     * the todo.
     *
     * @param processedInput tokenised user input containing the description
     * @return result string from the task list or validation feedback
     */
    @Override
    public String execute(String[] processedInput) {
        try {
            // Validate minimum arguments
            String validationError = validateMinimumArgs(
                    processedInput,
                    "Todo description cannot be empty. Usage: todo [description]");
            if (validationError != null) {
                return validationError;
            }

            String description = processedInput[ARGS_INDEX].trim();

            // Validate description length
            if (description.length() > MAX_DESCRIPTION_LENGTH) {
                String errorMsg = "Todo description too long. Please keep it under "
                        + MAX_DESCRIPTION_LENGTH
                        + " characters.";
                print(errorMsg);
                return errorMsg;
            }

            // Check for potentially problematic characters
            if (description.contains("|") || description.contains("\\n")) {
                String errorMsg = "Todo description contains invalid characters. "
                        + "Avoid using | or line breaks.";
                print(errorMsg);
                return errorMsg;
            }

            String todoResult = toDoList.addToDos(description);
            print(todoResult);
            return todoResult;

        } catch (Exception e) {
            String errorMsg = "Error adding todo: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
