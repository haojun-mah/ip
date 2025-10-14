package audrey.command;

import java.util.ArrayList;

import audrey.task.List;
import audrey.task.Task;

/** Command to find tasks by keyword. */
public class FindCommand extends BaseCommand {

    public FindCommand(List toDoList) {
        super(toDoList);
    }

    @Override
    public String execute(String[] processedInput) {
        try {
            // Validate minimum arguments
            String validationError =
                    validateMinimumArgs(
                            processedInput, "Find requires a keyword. Usage: find [keyword]");
            if (validationError != null) {
                return validationError;
            }

            String keyword = processedInput[ARGS_INDEX].trim();

            if (keyword.isEmpty()) {
                String errorMsg = "Find keyword cannot be empty.";
                print(errorMsg);
                return errorMsg;
            }

            ArrayList<Task> foundTasks = toDoList.findTasks(keyword);

            if (foundTasks.isEmpty()) {
                String noMatchMsg = "No tasks found matching: " + keyword;
                print(noMatchMsg);
                return noMatchMsg;
            }

            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append("Here are the matching tasks in your list:\n");

            for (int i = 0; i < foundTasks.size(); i++) {
                resultBuilder.append(String.format("%d. %s\n", i + 1, foundTasks.get(i)));
            }

            String findResult = resultBuilder.toString().trim();
            print(findResult);
            return findResult;

        } catch (Exception e) {
            String errorMsg = "Error finding tasks: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
