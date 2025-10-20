package audrey.command;

import audrey.task.List;

/** Command that snoozes a task indefinitely or until a specified date. */
public class SnoozeCommand extends BaseCommand {

    /**
     * Builds a command that updates snooze status on tasks.
     *
     * @param toDoList backing task list to update
     */
    public SnoozeCommand(List toDoList) {
        super(toDoList);
    }

    /**
     * Parses the task number and optional date, then snoozes the matching task.
     *
     * @param processedInput tokenised user input containing task number and optional date
     * @return result string from the task list or validation feedback
     */
    @Override
    public String execute(String[] processedInput) {
        try {
            // Validate minimum arguments
            String validationError = validateMinimumArgs(
                    processedInput,
                    "Snooze requires a task number. Usage: snooze [task number] [optional: YYYY-MM-DD]");
            if (validationError != null) {
                return validationError;
            }

            String fullArgs = processedInput[ARGS_INDEX].trim();
            String[] parts = fullArgs.split("\\s+", 2);

            String numberStr = parts[0];

            // Validate task number
            String taskValidationError = validateTaskNumber(numberStr);
            if (taskValidationError != null) {
                return taskValidationError;
            }

            int taskNumber = Integer.parseInt(numberStr);
            String snoozeResult;

            // Check if date is provided
            if (parts.length > 1) {
                String dateStr = parts[1].trim();

                // Validate date format
                if (!dateStr.matches(DATE_PATTERN)) {
                    String errorMsg = "Invalid date format: '" + dateStr + "'. Use YYYY-MM-DD format.";
                    print(errorMsg);
                    return errorMsg;
                }

                snoozeResult = toDoList.snoozeTaskUntil(taskNumber, dateStr);
            } else {
                snoozeResult = toDoList.snoozeTaskForever(taskNumber);
            }

            print(snoozeResult);
            return snoozeResult;

        } catch (Exception e) {
            String errorMsg = "Error snoozing task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
