package audrey.command;

import audrey.task.List;

/** Command to list all tasks. */
public class ListCommand extends BaseCommand {

    public ListCommand(List toDoList) {
        super(toDoList);
    }

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
