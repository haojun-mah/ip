package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser that handles invalid or unrecognised commands. */
public class InvalidCommandParser extends BaseCommandParser {

    /**
     * Builds a parser to surface helpful feedback when commands are not recognised.
     *
     * @param toDoList backing task list (unused but kept for parity with other parsers)
     * @param scanner  scanner providing raw user input
     */
    public InvalidCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Reports that the command is invalid and returns formatted feedback.
     *
     * @param processedInput tokenised user input with the original command word
     * @return friendly error message describing the unrecognised command
     */
    @Override
    public String execute(String[] processedInput) {
        String input = processedInput.length > 0 ? processedInput[0] : "unknown";
        return handleInvalidCommand(input);
    }

    /**
     * Provides the default response for invalid commands.
     *
     * @param input original command string supplied by the user
     * @return formatted response indicating the command is not understood
     */
    private String handleInvalidCommand(String input) {
        String errorMsg = "I don't know what '" + input + "' means :-(";
        print(errorMsg);
        return errorMsg;
    }
}
