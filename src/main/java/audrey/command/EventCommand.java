package audrey.command;

import audrey.task.List;

/** Command to add an event task. */
public class EventCommand extends BaseCommand {

    public EventCommand(List toDoList) {
        super(toDoList);
    }

    @Override
    public String execute(String[] processedInput) {
        try {
            // Validate minimum arguments
            String validationError =
                    validateMinimumArgs(
                            processedInput,
                            "Event description cannot be empty. Usage: event [description] /from [start] /to [end]");
            if (validationError != null) {
                return validationError;
            }

            String fullDescription = processedInput[ARGS_INDEX].trim();

            // Validate /from and /to format
            if (!fullDescription.contains("/from") || !fullDescription.contains("/to")) {
                String errorMsg =
                        "Event must include both '/from [start]' and '/to [end]'. "
                                + "Usage: event [description] /from [YYYY-MM-DD] /to [YYYY-MM-DD]";
                print(errorMsg);
                return errorMsg;
            }

            // Check for multiple /from or /to occurrences
            if (fullDescription.split("/from").length > 2) {
                String errorMsg = "Multiple '/from' found. Please use '/from' only once.";
                print(errorMsg);
                return errorMsg;
            }

            if (fullDescription.split("/to").length > 2) {
                String errorMsg = "Multiple '/to' found. Please use '/to' only once.";
                print(errorMsg);
                return errorMsg;
            }

            // Parse the description with /from and /to
            String[] fromSplit = fullDescription.split("/from", 2);
            if (fromSplit.length != 2) {
                String errorMsg = "Invalid event format. Missing '/from' section.";
                print(errorMsg);
                return errorMsg;
            }

            String description = fromSplit[0].trim();
            String remainingPart = fromSplit[1].trim();

            String[] toSplit = remainingPart.split("/to", 2);
            if (toSplit.length != 2) {
                String errorMsg = "Invalid event format. Missing '/to' section.";
                print(errorMsg);
                return errorMsg;
            }

            String startDateStr = toSplit[0].trim();
            String endDateStr = toSplit[1].trim();

            if (description.isEmpty()) {
                String errorMsg = "Event description cannot be empty.";
                print(errorMsg);
                return errorMsg;
            }

            if (startDateStr.isEmpty()) {
                String errorMsg = "Start date cannot be empty. Use format: YYYY-MM-DD";
                print(errorMsg);
                return errorMsg;
            }

            if (endDateStr.isEmpty()) {
                String errorMsg = "End date cannot be empty. Use format: YYYY-MM-DD";
                print(errorMsg);
                return errorMsg;
            }

            // Validate date format
            if (!startDateStr.matches(DATE_PATTERN)) {
                String errorMsg =
                        "Invalid start date format: '" + startDateStr + "'. Use YYYY-MM-DD format.";
                print(errorMsg);
                return errorMsg;
            }

            if (!endDateStr.matches(DATE_PATTERN)) {
                String errorMsg =
                        "Invalid end date format: '" + endDateStr + "'. Use YYYY-MM-DD format.";
                print(errorMsg);
                return errorMsg;
            }

            String eventResult = toDoList.addEvent(fullDescription);
            print(eventResult);
            return eventResult;

        } catch (Exception e) {
            String errorMsg = "Error adding event: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
