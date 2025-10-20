package audrey.command;

import audrey.task.List;

/** Command that removes a task specified by its list index. */
public class DeleteCommand extends BaseCommand {

    /**
     * Builds a command that deletes tasks from the backing list.
     *
     * @param toDoList backing task list to update
     */
    public DeleteCommand(List toDoList) {
        super(toDoList);
    }

    /**
     * Validates the task index supplied and removes the matching entry from the list.
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
