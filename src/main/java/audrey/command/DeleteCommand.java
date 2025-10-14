package audrey.command;

import audrey.task.List;

/** Command to delete a task. */
public class DeleteCommand extends BaseCommand {

    public DeleteCommand(List toDoList) {
        super(toDoList);
    }

    @Override
    public String execute(String[] processedInput) {
        try {
            // Validate minimum arguments
            String validationError =
                    validateMinimumArgs(
                            processedInput,
                            "Delete requires a task number. Usage: delete [task number]");
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
            String deleteResult = toDoList.delete(taskNumber);
            print(deleteResult);
            return deleteResult;

        } catch (Exception e) {
            String errorMsg = "Error deleting task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
