package audrey.command;

import audrey.task.List;

/** Command to add a deadline task. */
public class DeadlineCommand extends BaseCommand {

    public DeadlineCommand(List toDoList) {
        super(toDoList);
    }

    @Override
    public String execute(String[] processedInput) {
        try {
            // Validate minimum arguments
            String validationError =
                    validateMinimumArgs(
                            processedInput,
                            "Deadline description cannot be empty. Usage: deadline [description] /by [date]");
            if (validationError != null) {
                return validationError;
            }

            String fullDescription = processedInput[ARGS_INDEX].trim();

            // Validate /by format
            if (!fullDescription.contains("/by")) {
                String errorMsg =
                        "Deadline must include '/by [date]'. "
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
                String errorMsg =
                        "Invalid deadline format. "
                                + "Usage: deadline [description] /by [YYYY-MM-DD]";
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

            String deadlineResult = toDoList.addDeadline(fullDescription);
            print(deadlineResult);
            return deadlineResult;

        } catch (Exception e) {
            String errorMsg = "Error adding deadline: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
