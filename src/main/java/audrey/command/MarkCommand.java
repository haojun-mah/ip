package audrey.command;

import audrey.task.List;

/** Command to mark a task as completed. */
public class MarkCommand extends BaseCommand {

    public MarkCommand(List toDoList) {
        super(toDoList);
    }

    @Override
    public String execute(String[] processedInput) {
        try {
            // Validate minimum arguments
            String validationError =
                    validateMinimumArgs(
                            processedInput, "Task number required. Usage: mark [task number]");
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
            String markResult = toDoList.markTask(taskNumber);
            print(markResult);
            return markResult;

        } catch (NumberFormatException e) {
            String errorMsg =
                    "Invalid number format: '"
                            + processedInput[ARGS_INDEX]
                            + "'. Please enter a valid task number.";
            print(errorMsg);
            return errorMsg;
        } catch (Exception e) {
            String errorMsg = "Error marking task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
