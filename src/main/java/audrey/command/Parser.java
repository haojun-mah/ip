package audrey.command;

import java.util.Scanner;

import audrey.task.List;

/** Parser class encapsulates command logic */
public class Parser {
    // Constants for validation limits
    private static final int MAX_WHITESPACE_EXCESS = 10;
    private static final int MAX_COMMAND_LENGTH = 1000;

    // Constants for formatting
    private static final String INDENT = "    ";
    private static final String SEPARATOR_LINE =
            "    ____________________________________________________________________";

    // Constants for command strings
    private static final String HELP_COMMAND = "help";
    private static final String LIST_COMMAND = "list";

    // Constants for array operations
    private static final int SPLIT_LIMIT_TWO = 2;
    private static final int COMMAND_INDEX = 0;

    private final Scanner scanner;
    private final List toDoList;
    private boolean isListMode;

    /**
     * Creates parser instance
     *
     * @param toDoList list extracted from DB
     */
    public Parser(List toDoList) {
        // Assert: Todo list should not be null
        assert toDoList != null : "Todo list cannot be null";

        scanner = new Scanner(System.in);
        this.toDoList = toDoList;
        this.isListMode = false;

        // Assert: Parser should be properly initialized
        assert this.toDoList != null : "Parser todo list should be properly initialized";
        assert this.scanner != null : "Scanner should be properly initialized";
    }

    /**
     * Prettier print for CLI.
     *
     * @param string Text to print
     */
    private void print(String string) {
        String[] splitString = string.split("\n");
        StringBuilder formattedString = new StringBuilder();
        for (int i = 0; i < splitString.length; i++) {
            if (i + 1 == splitString.length) {
                formattedString.append(INDENT).append(splitString[i]);
            } else {
                formattedString.append(INDENT).append(splitString[i]).append('\n');
            }
        }
        System.out.println(SEPARATOR_LINE);
        System.out.println(formattedString.toString());
        System.out.println(SEPARATOR_LINE);
    }

    /** Processes user input and returns appropriate response. */
    public String runInput(String input) {
        // Assert: Input should not be null
        assert input != null : "Input string cannot be null";

        // Validate and sanitize input
        String validationResult = validateInput(input);
        if (validationResult != null) {
            return validationResult;
        }

        String sanitizedInput = sanitizeInput(input);

        if (isHelpCommand(sanitizedInput)) {
            return getHelpMessage();
        } else if (isListActivationCommand(sanitizedInput)) {
            return activateListMode();
        } else if (isListMode) {
            return processListModeCommand(sanitizedInput);
        } else {
            return echoInput(sanitizedInput);
        }
    }

    /**
     * Validates input for basic issues and returns error message if invalid.
     *
     * @param input Raw user input
     * @return Error message if invalid, null if valid
     */
    private String validateInput(String input) {
        if (input == null) {
            return "Input cannot be null.";
        }

        if (input.trim().isEmpty()) {
            return "Please enter a command.";
        }

        // Check for excessive whitespace that might indicate formatting issues
        if (input.length() - input.trim().length() > MAX_WHITESPACE_EXCESS) {
            return "Too much whitespace. Please check your command format.";
        }

        // Check for extremely long inputs that might cause issues
        if (input.length() > MAX_COMMAND_LENGTH) {
            return "Command too long. Please keep commands under "
                    + MAX_COMMAND_LENGTH
                    + " characters.";
        }

        // Check for potentially problematic characters
        if (input.contains("\t")) {
            return "Tab characters not allowed. Please use spaces.";
        }

        return null; // Input is valid
    }

    /**
     * Sanitizes input by normalizing whitespace and removing leading/trailing spaces.
     *
     * @param input Raw user input
     * @return Sanitized input
     */
    private String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }

        // Trim leading and trailing whitespace
        String sanitized = input.trim();

        // Replace multiple consecutive spaces with single spaces
        sanitized = sanitized.replaceAll("\\s+", " ");

        return sanitized;
    }

    /** Checks if the input is a help command. */
    private boolean isHelpCommand(String input) {
        return HELP_COMMAND.equalsIgnoreCase(input.trim());
    }

    /** Checks if the input is a list activation command. */
    private boolean isListActivationCommand(String input) {
        return LIST_COMMAND.equalsIgnoreCase(input) && !isListMode;
    }

    /** Activates list mode and returns the activation message. */
    private String activateListMode() {
        isListMode = true;
        print("To Do List Activated!");

        // Assert: After activation, list mode should be true
        assert isListMode : "List mode should be activated";

        return "To Do List Activated!\n\n" + toDoList.showList();
    }

    /** Processes commands when in list mode. */
    private String processListModeCommand(String input) {
        try {
            String[] processedInput = parseCommandAndArguments(input);
            String commandString = processedInput[0].toLowerCase();
            Command command = Command.fromString(commandString);

            if (command == null) {
                return handleInvalidCommand(input);
            }

            return executeCommand(command, processedInput);
        } catch (Exception e) {
            return "Error processing command: " + e.getMessage();
        }
    }

    /**
     * Parses command and arguments with enhanced error handling.
     *
     * @param input User input
     * @return Array with command as first element and arguments as subsequent elements
     */
    private String[] parseCommandAndArguments(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new String[] {""};
        }

        // Split on spaces but preserve quoted strings
        String[] parts = input.split(" ", SPLIT_LIMIT_TWO);

        // Validate command part
        if (parts[0].isEmpty()) {
            throw new IllegalArgumentException("Command cannot be empty");
        }

        // Check for invalid characters in command
        if (!parts[COMMAND_INDEX].matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException(
                    "Command must contain only letters: " + parts[COMMAND_INDEX]);
        }

        return parts;
    }

    /** Executes the given command with the processed input using command parsers. */
    private String executeCommand(Command command, String[] processedInput) {
        BaseCommandParser commandParser;

        switch (command) {
            case BYE:
                print("To Do List Deactivated");
                isListMode = false;
                return "To Do List Deactivated!";
            case LIST:
                commandParser = new ListCommandParser(toDoList, scanner);
                break;
            case MARK:
                commandParser = new MarkCommandParser(toDoList, scanner);
                break;
            case UNMARK:
                commandParser = new UnmarkCommandParser(toDoList, scanner);
                break;
            case TODO:
                commandParser = new TodoCommandParser(toDoList, scanner);
                break;
            case DEADLINE:
                commandParser = new DeadlineCommandParser(toDoList, scanner);
                break;
            case EVENT:
                commandParser = new EventCommandParser(toDoList, scanner);
                break;
            case DELETE:
                commandParser = new DeleteCommandParser(toDoList, scanner);
                break;
            case FIND:
                commandParser = new FindCommandParser(toDoList, scanner);
                break;
            case SNOOZE:
                commandParser = new SnoozeCommandParser(toDoList, scanner);
                break;
            case UNSNOOZE:
                commandParser = new UnsnoozeCommandParser(toDoList, scanner);
                break;
            case HELP:
                commandParser = new HelpCommandParser(toDoList, scanner);
                break;
            default:
                commandParser = new InvalidCommandParser(toDoList, scanner);
                break;
        }

        return commandParser.execute(processedInput);
    }

    /**
     * Returns a help message showing all available commands.
     *
     * @return String containing help information
     */
    private String getHelpMessage() {
        return """
                Available Commands
                ==================

                Task Management:
                • list - Show all tasks
                • todo <description> - Add a todo task
                • deadline <description> /by <date> - Add task with deadline
                • event <description> /from <date> /to <date> - Add an event

                Task Actions:
                • mark <number> - Mark task as completed
                • unmark <number> - Mark task as not done
                • delete <number> - Remove task permanently

                Find & Organize:
                • find <keyword> - Search for tasks
                • snooze <number> - Snooze task forever
                • snooze <number> <date> - Snooze until specific date
                • unsnooze <number> - Remove snooze status from task

                Other Commands:
                • help - Show this help guide
                • bye - Exit the application

                Examples:
                • todo Read book
                • deadline Submit assignment /by 2025-09-25
                • event Meeting /from 2025-09-20 /to 2025-09-20
                • snooze 1 2025-10-01

                Note: Dates should be in YYYY-MM-DD format
                """;
    }

    /** Handles invalid commands with specific input context. */
    private String handleInvalidCommand(String input) {
        String message =
                "Invalid command: '"
                        + input
                        + "'. Type '"
                        + HELP_COMMAND
                        + "' to see available commands.";
        print(message);
        return message;
    }

    /** Echoes the input back to the user (when not in list mode). */
    private String echoInput(String input) {
        print(input);
        return input;
    }
}
