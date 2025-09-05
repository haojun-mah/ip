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
     * @param toDoList
     *            list extracted from DB
     */
    public Parser(List toDoList) {
        scanner = new Scanner(System.in);
        this.toDoList = toDoList;
        this.isListMode = false;
    }

    /**
     * Prettier print for CLI.
     *
     * @param string
     *            Text to print
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
     * Run command loop
     */
    public String runInput(String input) {
        if ("list".equalsIgnoreCase(input) && !isListMode) {
            isListMode = true;
            print("To Do List Activated!");
            return "To Do List Activated!\n\n" + toDoList.showList();
        } else if (isListMode) {
            String[] processedInput = input.split(" ", 2);
            String detectMark = processedInput[0];
            Command command = Command.fromString(detectMark);
            if (command == null) {
                print("Invalid Task");
                return "Invalid Task!";
            } else {
                switch (command) {
                case BYE :
                    print("To Do List Deactivated");
                    isListMode = false;
                    return "To Do List Deactivated!";
                case LIST :
                    print(toDoList.showList());
                    return toDoList.showList();
                case MARK :
                    try {
                        String markResult = toDoList.markTask(Integer.parseInt(processedInput[1]));
                        print(markResult);
                        return markResult;
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        String errorMsg = "Number not provided!";
                        print(errorMsg);
                        return errorMsg;
                    }
                case UNMARK :
                    try {
                        String unmarkResult = toDoList.unmarkTask(Integer.parseInt(processedInput[1]));
                        print(unmarkResult);
                        return unmarkResult;
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        String errorMsg = "Number not provided!";
                        print(errorMsg);
                        return errorMsg;
                    }
                case TODO :
                    if (processedInput.length > 1) {
                        String todoResult = toDoList.addToDos(processedInput[1]);
                        print(todoResult);
                        return todoResult;
                    } else {
                        String errorMsg = "Todo description cannot be empty!";
                        print(errorMsg);
                        return errorMsg;
                    }
                case DEADLINE :
                    if (processedInput.length > 1) {
                        String deadlineResult = toDoList.addDeadline(processedInput[1]);
                        print(deadlineResult);
                        return deadlineResult;
                    } else {
                        String errorMsg = "Deadline description cannot be empty!";
                        print(errorMsg);
                        return errorMsg;
                    }
                case EVENT :
                    if (processedInput.length > 1) {
                        String eventResult = toDoList.addEvent(processedInput[1]);
                        print(eventResult);
                        return eventResult;
                    } else {
                        String errorMsg = "Event description cannot be empty!";
                        print(errorMsg);
                        return errorMsg;
                    }
                case DELETE :
                    try {
                        String deleteResult = toDoList.delete(Integer.parseInt(processedInput[1]));
                        print(deleteResult);
                        return deleteResult;
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        String errorMsg = "Number not provided!";
                        print(errorMsg);
                        return errorMsg;
                    }
                case FIND :
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
                default :
                    String defaultMsg = "Unknown command";
                    print(defaultMsg);
                    return defaultMsg;
                }
            }
        } else {
            print(input);
            return input;
        }
    }
}
