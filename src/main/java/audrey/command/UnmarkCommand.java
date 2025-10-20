package audrey.command;

import audrey.task.List;

/** Command that marks a task as not completed. */
public class UnmarkCommand extends BaseCommand {

    /**
     * Builds a command that validates and unmarks tasks.
     *
     * @param toDoList backing task list to update
     */
    public UnmarkCommand(List toDoList) {
        super(toDoList);
    }

    /**
     * Validates the task index and marks the matching task as not done.
     *
     * @param processedInput tokenised user input containing the task index
     * @return result string from the task list or validation feedback
     */
    @Override
    public String execute(String[] processedInput) {
        try {
            // Validate minimum arguments
            String validationError = validateMinimumArgs(
                    processedInput, "Task number required. Usage: unmark [task number]");
            if (validationError != null) {
                return validationError;
            }

            String taskNumberStr = processedInput[ARGS_INDEX].trim();

            // Validate task number format and range
            String taskValidationError = validateTaskNumber(taskNumberStr);
            if (taskValidationError != null) {
                return taskValidationError;
            }

            int taskNumber = Integer.parseInt(taskNumberStr);
            String unmarkResult = toDoList.unmarkTask(taskNumber);
            print(unmarkResult);
            return unmarkResult;

        } catch (NumberFormatException e) {
            String errorMsg = "Invalid number format: '"
                    + processedInput[ARGS_INDEX]
                    + "'. Please enter a valid task number.";
            print(errorMsg);
            return errorMsg;
        } catch (Exception e) {
            String errorMsg = "Error unmarking task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
