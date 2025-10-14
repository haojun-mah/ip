package audrey.command;

import java.util.ArrayList;
import java.util.Scanner;

import audrey.task.List;
import audrey.task.Task;

/** Parser class encapsulates command logic */
public class Parser {
    // Constants for validation limits
    private static final int MAX_WHITESPACE_EXCESS = 10;
    private static final int MAX_COMMAND_LENGTH = 1000;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    // Constants for formatting
    private static final String INDENT = "    ";
    private static final String SEPARATOR_LINE =
            "    ____________________________________________________________________";

    // Constants for date format
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    // Constants for command strings
    private static final String HELP_COMMAND = "help";
    private static final String LIST_COMMAND = "list";

    // Constants for array operations
    private static final int SPLIT_LIMIT_TWO = 2;
    private static final int COMMAND_INDEX = 0;
    private static final int ARGS_INDEX = 1;
    private static final int MIN_ARGS_LENGTH = 2;

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

    /** Executes the given command with the processed input. */
    private String executeCommand(Command command, String[] processedInput) {
        switch (command) {
            case BYE:
                print("To Do List Deactivated");
                isListMode = false;
                return "To Do List Deactivated!";
            case LIST:
                print(toDoList.showList());
                return toDoList.showList();
            case MARK:
                return handleMarkCommand(processedInput);
            case UNMARK:
                return handleUnmarkCommand(processedInput);
            case TODO:
                return handleTodoCommand(processedInput);
            case DEADLINE:
                return handleDeadlineCommand(processedInput);
            case EVENT:
                return handleEventCommand(processedInput);
            case DELETE:
                return handleDeleteCommand(processedInput);
            case FIND:
                return handleFindCommand(processedInput);
            case SNOOZE:
                return handleSnoozeCommand(processedInput);
            case UNSNOOZE:
                return handleUnsnoozeCommand(processedInput);
            case HELP:
                return handleHelpCommand();
            default:
                return handleUnknownCommand();
        }
    }

    /**
     * Handles MARK command with comprehensive error checking.
     *
     * @param processedInput Command and arguments
     * @return Result message
     */
    private String handleMarkCommand(String[] processedInput) {
        try {
            // Validate argument exists
            if (processedInput.length < 2 || processedInput[1].trim().isEmpty()) {
                String errorMsg = "Task number required. Usage: mark [task number]";
                print(errorMsg);
                return errorMsg;
            }

            String taskNumberStr = processedInput[1].trim();

            // Validate task number format
            if (!taskNumberStr.matches("\\d+")) {
                String errorMsg =
                        "Invalid task number format: '"
                                + taskNumberStr
                                + "'. Please enter a positive integer.";
                print(errorMsg);
                return errorMsg;
            }

            int taskNumber = Integer.parseInt(taskNumberStr);

            // Validate task number range
            if (taskNumber <= 0) {
                String errorMsg = "Task number must be positive. You entered: " + taskNumber;
                print(errorMsg);
                return errorMsg;
            }

            if (taskNumber > toDoList.size()) {
                String errorMsg =
                        "Task number "
                                + taskNumber
                                + " does not exist. You have "
                                + toDoList.size()
                                + " tasks.";
                print(errorMsg);
                return errorMsg;
            }

            String markResult = toDoList.markTask(taskNumber);
            print(markResult);
            return markResult;

        } catch (NumberFormatException e) {
            String errorMsg =
                    "Invalid number format: '"
                            + processedInput[1]
                            + "'. Please enter a valid task number.";
            print(errorMsg);
            return errorMsg;
        } catch (Exception e) {
            String errorMsg = "Error marking task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }

    /**
     * Handles UNMARK command with comprehensive error checking.
     *
     * @param processedInput Command and arguments
     * @return Result message
     */
    private String handleUnmarkCommand(String[] processedInput) {
        try {
            // Validate argument exists
            if (processedInput.length < 2 || processedInput[1].trim().isEmpty()) {
                String errorMsg = "Task number required. Usage: unmark [task number]";
                print(errorMsg);
                return errorMsg;
            }

            String taskNumberStr = processedInput[1].trim();

            // Validate task number format
            if (!taskNumberStr.matches("\\d+")) {
                String errorMsg =
                        "Invalid task number format: '"
                                + taskNumberStr
                                + "'. Please enter a positive integer.";
                print(errorMsg);
                return errorMsg;
            }

            int taskNumber = Integer.parseInt(taskNumberStr);

            // Validate task number range
            if (taskNumber <= 0) {
                String errorMsg = "Task number must be positive. You entered: " + taskNumber;
                print(errorMsg);
                return errorMsg;
            }

            if (taskNumber > toDoList.size()) {
                String errorMsg =
                        "Task number "
                                + taskNumber
                                + " does not exist. You have "
                                + toDoList.size()
                                + " tasks.";
                print(errorMsg);
                return errorMsg;
            }

            String unmarkResult = toDoList.unmarkTask(taskNumber);
            print(unmarkResult);
            return unmarkResult;

        } catch (NumberFormatException e) {
            String errorMsg =
                    "Invalid number format: '"
                            + processedInput[1]
                            + "'. Please enter a valid task number.";
            print(errorMsg);
            return errorMsg;
        } catch (Exception e) {
            String errorMsg = "Error unmarking task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }

    /**
     * Handles TODO command with comprehensive validation.
     *
     * @param processedInput Command and arguments
     * @return Result message
     */
    private String handleTodoCommand(String[] processedInput) {
        try {
            if (processedInput.length < 2 || processedInput[1].trim().isEmpty()) {
                String errorMsg = "Todo description cannot be empty. Usage: todo [description]";
                print(errorMsg);
                return errorMsg;
            }

            String description = processedInput[1].trim();

            // Validate description length
            if (description.length() > MAX_DESCRIPTION_LENGTH) {
                String errorMsg =
                        "Todo description too long. Please keep it under "
                                + MAX_DESCRIPTION_LENGTH
                                + " characters.";
                print(errorMsg);
                return errorMsg;
            }

            // Check for potentially problematic characters
            if (description.contains("|") || description.contains("\\n")) {
                String errorMsg =
                        "Todo description contains invalid characters. Avoid using | or line breaks.";
                print(errorMsg);
                return errorMsg;
            }

            String todoResult = toDoList.addToDos(description);
            print(todoResult);
            return todoResult;

        } catch (Exception e) {
            String errorMsg = "Error adding todo: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }

    /**
     * Handles DEADLINE command with comprehensive validation.
     *
     * @param processedInput Command and arguments
     * @return Result message
     */
    private String handleDeadlineCommand(String[] processedInput) {
        try {
            if (processedInput.length < 2 || processedInput[1].trim().isEmpty()) {
                String errorMsg =
                        "Deadline description cannot be empty. Usage: deadline [description] /by [date]";
                print(errorMsg);
                return errorMsg;
            }

            String fullDescription = processedInput[1].trim();

            // Validate /by format
            if (!fullDescription.contains("/by")) {
                String errorMsg =
                        "Deadline must include '/by [date]'. Usage: deadline [description] /by [YYYY-MM-DD]";
                print(errorMsg);
                return errorMsg;
            }

            // Check for multiple /by occurrences
            if (fullDescription.split("/by").length > 2) {
                String errorMsg = "Multiple '/by' found. Please use '/by' only once.";
                print(errorMsg);
                return errorMsg;
            }

            String[] parts = fullDescription.split("/by", 2);
            if (parts.length != 2) {
                String errorMsg =
                        "Invalid deadline format. Usage: deadline [description] /by [YYYY-MM-DD]";
                print(errorMsg);
                return errorMsg;
            }

            String description = parts[0].trim();
            String dateStr = parts[1].trim();

            if (description.isEmpty()) {
                String errorMsg = "Deadline description cannot be empty.";
                print(errorMsg);
                return errorMsg;
            }

            if (dateStr.isEmpty()) {
                String errorMsg = "Date cannot be empty. Use format: YYYY-MM-DD";
                print(errorMsg);
                return errorMsg;
            }

            // Validate date format
            if (!dateStr.matches(DATE_PATTERN)) {
                String errorMsg = "Invalid date format: '" + dateStr + "'. Use YYYY-MM-DD format.";
                print(errorMsg);
                return errorMsg;
            }

            String deadlineResult = toDoList.addDeadline(fullDescription);
            print(deadlineResult);
            return deadlineResult;

        } catch (Exception e) {
            String errorMsg = "Error adding deadline: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }

    /**
     * Handles EVENT command with comprehensive validation.
     *
     * @param processedInput Command and arguments
     * @return Result message
     */
    private String handleEventCommand(String[] processedInput) {
        try {
            if (processedInput.length < 2 || processedInput[1].trim().isEmpty()) {
                String errorMsg =
                        "Event description cannot be empty. Usage: event [description] /from [date] /to [date]";
                print(errorMsg);
                return errorMsg;
            }

            String fullDescription = processedInput[1].trim();

            // Validate /from and /to format
            if (!fullDescription.contains("/from")) {
                String errorMsg =
                        "Event must include '/from [date]'. Usage: event [description] /from [date] /to [date]";
                print(errorMsg);
                return errorMsg;
            }

            if (!fullDescription.contains("/to")) {
                String errorMsg =
                        "Event must include '/to [date]'. Usage: event [description] /from [date] /to [date]";
                print(errorMsg);
                return errorMsg;
            }

            // Check for multiple occurrences
            if (fullDescription.split("/from").length > 2) {
                String errorMsg = "Multiple '/from' found. Please use '/from' only once.";
                print(errorMsg);
                return errorMsg;
            }

            if (fullDescription.split("/to").length > 2) {
                String errorMsg = "Multiple '/to' found. Please use '/to' only once.";
                print(errorMsg);
                return errorMsg;
            }

            // Parse the event string
            String[] fromSplit = fullDescription.split("/from", 2);
            if (fromSplit.length != 2) {
                String errorMsg =
                        "Invalid event format. Usage: event [description] /from [date] /to [date]";
                print(errorMsg);
                return errorMsg;
            }

            String description = fromSplit[0].trim();
            String remaining = fromSplit[1].trim();

            String[] toSplit = remaining.split("/to", 2);
            if (toSplit.length != 2) {
                String errorMsg =
                        "Invalid event format. Usage: event [description] /from [date] /to [date]";
                print(errorMsg);
                return errorMsg;
            }

            String fromDate = toSplit[0].trim();
            String toDate = toSplit[1].trim();

            // Validate components
            if (description.isEmpty()) {
                String errorMsg = "Event description cannot be empty.";
                print(errorMsg);
                return errorMsg;
            }

            if (fromDate.isEmpty()) {
                String errorMsg = "From date cannot be empty. Use format: YYYY-MM-DD";
                print(errorMsg);
                return errorMsg;
            }

            if (toDate.isEmpty()) {
                String errorMsg = "To date cannot be empty. Use format: YYYY-MM-DD";
                print(errorMsg);
                return errorMsg;
            }

            // Validate date formats
            if (!fromDate.matches(DATE_PATTERN)) {
                String errorMsg =
                        "Invalid from date format: '" + fromDate + "'. Use YYYY-MM-DD format.";
                print(errorMsg);
                return errorMsg;
            }

            if (!toDate.matches(DATE_PATTERN)) {
                String errorMsg =
                        "Invalid to date format: '" + toDate + "'. Use YYYY-MM-DD format.";
                print(errorMsg);
                return errorMsg;
            }

            String eventResult = toDoList.addEvent(fullDescription);
            print(eventResult);
            return eventResult;

        } catch (Exception e) {
            String errorMsg = "Error adding event: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
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

    /**
     * Handles DELETE command with comprehensive error checking.
     *
     * @param processedInput Command and arguments
     * @return Result message
     */
    private String handleDeleteCommand(String[] processedInput) {
        try {
            if (processedInput.length < MIN_ARGS_LENGTH
                    || processedInput[ARGS_INDEX].trim().isEmpty()) {
                String errorMsg = "Task number required. Usage: delete [task number]";
                print(errorMsg);
                return errorMsg;
            }

            int taskNumber = Integer.parseInt(processedInput[ARGS_INDEX].trim());
            String deleteResult = toDoList.delete(taskNumber);
            print(deleteResult);
            return deleteResult;

        } catch (NumberFormatException e) {
            String errorMsg = "Invalid task number format. Please enter a valid integer.";
            print(errorMsg);
            return errorMsg;
        } catch (Exception e) {
            String errorMsg = "Error deleting task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }

    /**
     * Handles FIND command with comprehensive validation.
     *
     * @param processedInput Command and arguments
     * @return Result message
     */
    private String handleFindCommand(String[] processedInput) {
        if (processedInput.length < 2 || processedInput[1].trim().isEmpty()) {
            String errorMsg = "Search keyword required. Usage: find [keyword]";
            print(errorMsg);
            return errorMsg;
        }

        String keyword = processedInput[1].trim();
        ArrayList<Task> findList = toDoList.findTasks(keyword);

        if (findList.isEmpty()) {
            String noResultMsg = "No matching task found!";
            print(noResultMsg);
            return noResultMsg;
        }

        StringBuilder printString =
                new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < findList.size(); i++) {
            printString.append(String.format("%d.%s\n", i + 1, findList.get(i)));
        }

        String result = printString.toString();
        print(result);
        return result;
    }

    /**
     * Handles SNOOZE command with comprehensive validation.
     *
     * @param processedInput Command and arguments
     * @return Result message
     */
    private String handleSnoozeCommand(String[] processedInput) {
        if (processedInput.length == 1) {
            return handleSnoozeList();
        }

        String[] snoozeParams = processedInput[1].split(" ");
        if (snoozeParams.length == 1) {
            return handleSnoozeForever(snoozeParams[0]);
        } else if (snoozeParams.length == 2) {
            return handleSnoozeUntilDate(snoozeParams[0], snoozeParams[1]);
        } else {
            String errorMsg =
                    "Invalid snooze command. Use: 'snooze', 'snooze [number]', or 'snooze [number] [date]'";
            print(errorMsg);
            return errorMsg;
        }
    }

    /** Shows list of snoozable tasks. */
    private String handleSnoozeList() {
        String snoozeableList = toDoList.showSnoozableTasks();
        print(snoozeableList);
        return snoozeableList;
    }

    /** Snoozes a task forever. */
    private String handleSnoozeForever(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            String snoozeResult = toDoList.snoozeTaskForever(taskNumber);
            print(snoozeResult);
            return snoozeResult;
        } catch (NumberFormatException e) {
            String errorMsg = "Invalid task number format!";
            print(errorMsg);
            return errorMsg;
        }
    }

    /** Snoozes a task until a specific date. */
    private String handleSnoozeUntilDate(String taskNumberStr, String date) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            String snoozeResult = toDoList.snoozeTaskUntil(taskNumber, date);
            print(snoozeResult);
            return snoozeResult;
        } catch (NumberFormatException e) {
            String errorMsg = "Invalid task number format!";
            print(errorMsg);
            return errorMsg;
        }
    }

    /**
     * Handles UNSNOOZE command with comprehensive validation.
     *
     * @param processedInput Command and arguments
     * @return Result message
     */
    private String handleUnsnoozeCommand(String[] processedInput) {
        if (processedInput.length < 2 || processedInput[1].trim().isEmpty()) {
            String errorMsg = "Task number required. Usage: unsnooze [task number]";
            print(errorMsg);
            return errorMsg;
        }

        try {
            int taskNumber = Integer.parseInt(processedInput[1].trim());
            String unsnoozeResult = toDoList.unsnoozeTask(taskNumber);
            print(unsnoozeResult);
            return unsnoozeResult;
        } catch (NumberFormatException e) {
            String errorMsg = "Invalid task number format!";
            print(errorMsg);
            return errorMsg;
        }
    }

    /** Handles HELP command. */
    private String handleHelpCommand() {
        String helpMsg = getHelpMessage();
        print(helpMsg);
        return helpMsg;
    }

    /** Handles unknown commands. */
    private String handleUnknownCommand() {
        String defaultMsg = "Unknown command. Type '" + HELP_COMMAND + "' for available commands.";
        print(defaultMsg);
        return defaultMsg;
    }
}
