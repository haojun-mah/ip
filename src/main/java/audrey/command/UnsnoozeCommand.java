package audrey.command;

import audrey.task.List;

/** Command that removes snooze status from a task. */
public class UnsnoozeCommand extends BaseCommand {

    /**
     * Builds a command that validates and unsnoozes tasks.
     *
     * @param toDoList backing task list to update
     */
    public UnsnoozeCommand(List toDoList) {
        super(toDoList);
    }

    /**
     * Validates the task index and clears the snooze state for the selected task.
     *
     * @param processedInput tokenised user input containing the task index
     * @return result string from the task list or validation feedback
     */
    @Override
    public String execute(String[] processedInput) {
        try {
            // Validate minimum arguments
            String validationError = validateMinimumArgs(
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
