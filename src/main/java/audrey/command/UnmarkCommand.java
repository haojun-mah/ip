package audrey.command;

import audrey.task.List;

/** Command to unmark a task (mark as not completed). */
public class UnmarkCommand extends BaseCommand {

    public UnmarkCommand(List toDoList) {
        super(toDoList);
    }

    @Override
    public String execute(String[] processedInput) {
        try {
            // Validate minimum arguments
            String validationError =
                    validateMinimumArgs(
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
            String errorMsg =
                    "Invalid number format: '"
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
