package audrey.parser;

import java.util.ArrayList;
import java.util.Scanner;

import audrey.task.List;
import audrey.task.Task;

/** Parser that locates tasks matching keywords from {@code find} commands. */
public class FindCommandParser extends BaseCommandParser {

    /**
     * Builds a parser that searches the task list for keyword matches.
     *
     * @param toDoList backing task list to query
     * @param scanner  scanner providing raw user input
     */
    public FindCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Parses and executes a find command, returning numbered matches or an
     * informative message when none exist.
     *
     * @param processedInput tokenised user input containing the keyword
     * @return formatted search results from the task list
     */
    @Override
    public String execute(String[] processedInput) {
        // Validate minimum arguments
        String validationError = validateMinimumArgs(
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

        try {
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
