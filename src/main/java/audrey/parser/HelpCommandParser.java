package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser that returns guidance for available commands when {@code help} is issued. */
public class HelpCommandParser extends BaseCommandParser {

    /**
     * Builds a parser that prints the supported commands.
     *
     * @param toDoList backing task list (unused but kept for parity with other parsers)
     * @param scanner  scanner providing raw user input
     */
    public HelpCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Executes the help command by returning a formatted list of supported
     * instructions.
     *
     * @param processedInput tokenised user input (ignored)
     * @return formatted help message containing usage information
     */
    @Override
    public String execute(String[] processedInput) {
        String helpMessage = "Here are the available commands:\n\n"
                + "1. todo [description] - Add a todo task\n"
                + "2. deadline [description] /by [YYYY-MM-DD] - Add a deadline task\n"
                + "3. event [description] /from [YYYY-MM-DD] /to [YYYY-MM-DD] - Add an event task\n"
                + "4. list - Show all tasks\n"
                + "5. mark [task number] - Mark a task as completed\n"
                + "6. unmark [task number] - Mark a task as not completed\n"
                + "7. delete [task number] - Delete a task\n"
                + "8. find [keyword] - Find tasks containing the keyword\n"
                + "9. snooze [task number] [optional: YYYY-MM-DD] - Snooze a task (forever or until date)\n"
                + "10. unsnooze [task number] - Unsnooze a task\n"
                + "11. help - Show this help message\n"
                + "12. bye - Exit the application\n\n"
                + "Note: Task numbers are based on the list shown by the 'list' command.";

        print(helpMessage);
        return helpMessage;
    }
}
