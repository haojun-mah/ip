package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser that renders the current task list when {@code list} is issued. */
public class ListCommandParser extends BaseCommandParser {

    /**
     * Builds a parser that delegates to the task list to display all entries.
     *
     * @param toDoList backing task list to query
     * @param scanner  scanner providing raw user input
     */
    public ListCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Executes the list command and returns the formatted task list or an error message.
     *
     * @param processedInput tokenised user input (ignored)
     * @return formatted task list or error message if retrieval fails
     */
    @Override
    public String execute(String[] processedInput) {
        try {
            String listResult = toDoList.showList();
            print(listResult);
            return listResult;

        } catch (Exception e) {
            String errorMsg = "Error listing tasks: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
