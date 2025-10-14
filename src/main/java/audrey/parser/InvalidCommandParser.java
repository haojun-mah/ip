package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser for invalid/unknown commands. */
public class InvalidCommandParser extends BaseCommandParser {

    public InvalidCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    @Override
    public String execute(String[] processedInput) {
        String input = processedInput.length > 0 ? processedInput[0] : "unknown";
        return handleInvalidCommand(input);
    }

    /** Handles invalid command input. */
    private String handleInvalidCommand(String input) {
        String errorMsg = "I don't know what '" + input + "' means :-(";
        print(errorMsg);
        return errorMsg;
    }
}
