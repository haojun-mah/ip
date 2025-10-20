package audrey.command;

import audrey.task.List;

/** Command that renders the full task list. */
public class ListCommand extends BaseCommand {

    /**
     * Builds a command that delegates to the task list to display all entries.
     *
     * @param toDoList backing task list to query
     */
    public ListCommand(List toDoList) {
        super(toDoList);
    }

    /**
     * Retrieves and prints the formatted task list.
     *
     * @param processedInput tokenised user input (ignored)
     * @return formatted list or error details if retrieval fails
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
