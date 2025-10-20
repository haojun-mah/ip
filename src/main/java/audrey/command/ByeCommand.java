package audrey.command;

import audrey.task.List;

/** Command that prints the farewell message when the user exits. */
public class ByeCommand extends BaseCommand {

    /**
     * Builds a command that emits a farewell message.
     *
     * @param toDoList backing task list (unused but kept for symmetry with other commands)
     */
    public ByeCommand(List toDoList) {
        super(toDoList);
    }

    /**
     * Prints a goodbye message and returns it to the caller.
     *
     * @param processedInput tokenised user input (ignored)
     * @return goodbye message presented to the user
     */
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
