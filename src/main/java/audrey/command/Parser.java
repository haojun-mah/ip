package audrey.command;

import java.util.ArrayList;
import java.util.Scanner;

import audrey.task.List;
import audrey.task.Task;

/**
 * Parser class encapsulates command logic
 */
public class Parser {
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
                formattedString.append("    ").append(splitString[i]);
            } else {
                formattedString.append("    ").append(splitString[i]).append('\n');
            }
        }
        System.out.println("    ____________________________________________________________________");
        System.out.println(formattedString.toString());
        System.out.println("    ____________________________________________________________________");
    }

    /**
     * Processes user input and returns appropriate response.
     */
    public String runInput(String input) {
        // Assert: Input should not be null
        assert input != null : "Input string cannot be null";

        if (isHelpCommand(input)) {
            return getHelpMessage();
        } else if (isListActivationCommand(input)) {
            return activateListMode();
        } else if (isListMode) {
            return processListModeCommand(input);
        } else {
            return echoInput(input);
        }
    }

    /**
     * Checks if the input is a help command.
     */
    private boolean isHelpCommand(String input) {
        return "help".equalsIgnoreCase(input.trim());
    }

    /**
     * Checks if the input is a list activation command.
     */
    private boolean isListActivationCommand(String input) {
        return "list".equalsIgnoreCase(input) && !isListMode;
    }

    /**
     * Activates list mode and returns the activation message.
     */
    private String activateListMode() {
        isListMode = true;
        print("To Do List Activated!");

        // Assert: After activation, list mode should be true
        assert isListMode : "List mode should be activated";

        return "To Do List Activated!\n\n" + toDoList.showList();
    }

    /**
     * Processes commands when in list mode.
     */
    private String processListModeCommand(String input) {
        String[] processedInput = input.split(" ", 2);
        String commandString = processedInput[0];
        Command command = Command.fromString(commandString);

        if (command == null) {
            return handleInvalidCommand();
        }

        return executeCommand(command, processedInput);
    }

    /**
     * Executes the given command with the processed input.
     */
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
            try {
                String markResult = toDoList.markTask(Integer.parseInt(processedInput[1]));
                print(markResult);
                return markResult;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                String errorMsg = "Number not provided!";
                print(errorMsg);
                return errorMsg;
            }
        case UNMARK:
            try {
                String unmarkResult = toDoList.unmarkTask(Integer.parseInt(processedInput[1]));
                print(unmarkResult);
                return unmarkResult;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                String errorMsg = "Number not provided!";
                print(errorMsg);
                return errorMsg;
            }
        case TODO:
            if (processedInput.length > 1) {
                String todoResult = toDoList.addToDos(processedInput[1]);
                print(todoResult);
                return todoResult;
            } else {
                String errorMsg = "Todo description cannot be empty!";
                print(errorMsg);
                return errorMsg;
            }
        case DEADLINE:
            if (processedInput.length > 1) {
                String deadlineResult = toDoList.addDeadline(processedInput[1]);
                print(deadlineResult);
                return deadlineResult;
            } else {
                String errorMsg = "Deadline description cannot be empty!";
                print(errorMsg);
                return errorMsg;
            }
        case EVENT:
            if (processedInput.length > 1) {
                String eventResult = toDoList.addEvent(processedInput[1]);
                print(eventResult);
                return eventResult;
            } else {
                String errorMsg = "Event description cannot be empty!";
                print(errorMsg);
                return errorMsg;
            }
        case DELETE:
            try {
                String deleteResult = toDoList.delete(Integer.parseInt(processedInput[1]));
                print(deleteResult);
                return deleteResult;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                String errorMsg = "Number not provided!";
                print(errorMsg);
                return errorMsg;
            }
        case FIND:
            if (processedInput.length > 1) {
                String printString = "Here are the matching tasks in your list:\n";
                ArrayList<Task> findList = toDoList.findTasks(processedInput[1].trim());
                if (findList.isEmpty()) {
                    String noResultMsg = "No matching task found!";
                    print(noResultMsg);
                    return noResultMsg;
                } else {
                    for (int i = 0; i < findList.size(); i++) {
                        printString += String.format("%d.%s\n", i + 1, findList.get(i));
                    }
                    print(printString);
                    return printString;
                }
            } else {
                String errorMsg = "Find description is empty";
                print(errorMsg);
                return errorMsg;
            }
        case SNOOZE:
            if (processedInput.length == 1) {
                // Just "snooze" - show snoozable tasks
                String snoozeableList = toDoList.showSnoozableTasks();
                print(snoozeableList);
                return snoozeableList;
            } else {
                // Parse snooze parameters manually
                String[] snoozeParams = processedInput[1].split(" ");
                if (snoozeParams.length == 1) {
                    // "snooze 1" - snooze task forever
                    try {
                        String snoozeResult = toDoList.snoozeTaskForever(Integer.parseInt(snoozeParams[0]));
                        print(snoozeResult);
                        return snoozeResult;
                    } catch (NumberFormatException e) {
                        String errorMsg = "Invalid task number!";
                        print(errorMsg);
                        return errorMsg;
                    }
                } else if (snoozeParams.length == 2) {
                    // "snooze 1 2024-12-25" - snooze task until date
                    try {
                        String snoozeResult = toDoList.snoozeTaskUntil(Integer.parseInt(snoozeParams[0]),
                                snoozeParams[1]);
                        print(snoozeResult);
                        return snoozeResult;
                    } catch (NumberFormatException e) {
                        String errorMsg = "Invalid task number!";
                        print(errorMsg);
                        return errorMsg;
                    }
                } else {
                    String errorMsg = "Invalid snooze command. Use: 'snooze', 'snooze 1', or 'snooze 1 YYYY-MM-DD'";
                    print(errorMsg);
                    return errorMsg;
                }
            }
        case UNSNOOZE:
            if (processedInput.length == 2) {
                try {
                    String unsnoozeResult = toDoList.unsnoozeTask(Integer.parseInt(processedInput[1]));
                    print(unsnoozeResult);
                    return unsnoozeResult;
                } catch (NumberFormatException e) {
                    String errorMsg = "Invalid task number!";
                    print(errorMsg);
                    return errorMsg;
                }
            } else {
                String errorMsg = "Invalid unsnooze command. Use: 'unsnooze [task number]'";
                print(errorMsg);
                return errorMsg;
            }
        case HELP:
            String helpMsg = getHelpMessage();
            print(helpMsg);
            return helpMsg;
        default:
            String defaultMsg = "Unknown command";
            print(defaultMsg);
            return defaultMsg;
        }
    }

    /**
     * Returns a help message showing all available commands.
     *
     * @return String containing help information
     */
    private String getHelpMessage() {
        return """
                Available Commands:
                ==================
                • list - Show all tasks
                • todo <description> - Add a todo task
                • deadline <description> /by <date> - Add a deadline task (date: YYYY-MM-DD)
                • event <description> /from <date> /to <date> - Add an event task
                • mark <number> - Mark task as done
                • unmark <number> - Mark task as not done
                • delete <number> - Delete a task
                • find <keyword> - Find tasks containing keyword
                • snooze <number> - Snooze task forever
                • snooze <number> <date> - Snooze task until date (YYYY-MM-DD)
                • unsnooze <number> - Remove snooze status from task
                • help - Show this help message
                • bye - Exit the application

                Examples:
                • todo Read book
                • deadline Submit assignment /by 2025-09-25
                • event Meeting /from 2025-09-20 /to 2025-09-20
                • snooze 1 2025-10-01
                """;
    }

    /**
     * Handles invalid commands.
     */
    private String handleInvalidCommand() {
        print("Invalid Task");
        return "Invalid Task!";
    }

    /**
     * Echoes the input back to the user (when not in list mode).
     */
    private String echoInput(String input) {
        print(input);
        return input;
    }
}
