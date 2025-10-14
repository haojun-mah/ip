package audrey.command;

import audrey.task.List;

/** Command to exit the application. */
public class ByeCommand extends BaseCommand {

    public ByeCommand(List toDoList) {
        super(toDoList);
    }

    @Override
    public String execute(String[] processedInput) {
        try {
            String byeMessage = "Bye. Hope to see you again soon!";
            print(byeMessage);
            return byeMessage;

        } catch (Exception e) {
            String errorMsg = "Error saying goodbye: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
