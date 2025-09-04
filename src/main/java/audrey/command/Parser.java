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

    public Parser(List toDoList) {
        scanner = new Scanner(System.in);
        this.toDoList = toDoList;
    }

    /**
<<<<<<< HEAD
     * Prettier print for CLI
     * 
     * @param string text to print
=======
     * Prettier print for CLI.
     *
     * @param string
     *            Text to print
>>>>>>> branch-A-CheckStyle
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
    public void runInput() {
        String input = scanner.nextLine();

        while (true) {
            if ("bye".equalsIgnoreCase(input)) {
                break;
            } else if ("list".equalsIgnoreCase(input)) {
                print("To Do List Activated!");

                while (true) {
                    input = scanner.nextLine();
                    String[] processedInput = input.split(" ", 2); // obtain
                                                                   // first
                                                                   // string of
                                                                   // words
                                                                   // before
                                                                   // whitespace
                    String detectMark = processedInput[0];
                    Command command = Command.fromString(detectMark);

                    if (command == null) {
                        print("Invalid Task");
                    } else if (command == Command.BYE) {
                        input = "";
                        print("To Do List Deactivated");
                        break;
                    } else {
                        switch (command) {
                        case BYE :
                            break;
                        case LIST :
                            print(toDoList.showList());
                            break;
                        case MARK :
                            try {
                                print(toDoList.markTask(Integer.parseInt(processedInput[1])));
                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                                print("Number not provided!");
                            }
                            break;
                        case UNMARK :
                            try {
                                print(toDoList.unmarkTask(Integer.parseInt(processedInput[1])));
                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                                print("Number not provided!");
                            }
                            break;
                        case TODO :
                            if (processedInput.length > 1) {
                                print(toDoList.addToDos(processedInput[1]));
                            } else {
                                print("Todo description cannot be empty!");
                            }
                            break;
                        case DEADLINE :
                            if (processedInput.length > 1) {
                                print(toDoList.addDeadline(processedInput[1]));
                            } else {
                                print("Deadline description cannot be empty!");
                            }
                            break;
                        case EVENT :
                            if (processedInput.length > 1) {
                                print(toDoList.addEvent(processedInput[1]));
                            } else {
                                print("Event description cannot be empty!");
                            }
                            break;
                        case DELETE :
                            try {
                                print(toDoList.delete(Integer.parseInt(processedInput[1])));
                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                                print("Number not provided!");
                            }
                            break;
                        case FIND :
                            if (processedInput.length > 1) {
                                String printString = "Here are the matching tasks in your list:\n";
                                ArrayList<Task> findList = toDoList.findTasks(processedInput[1].trim());
                                if (findList.isEmpty()) {
                                    print("No matching task found!");
                                } else {
                                    for (int i = 0; i < findList.size(); i++) {
                                        printString += String.format("%d.%s\n", i + 1, findList.get(i));
                                    }
                                    print(printString);
                                }
                            } else {
                                print("Find description is empty");
                            }
                            break;
                        default :
                            break;
                        }
                    }
                }
            } else {
                print(input);
                input = scanner.nextLine();
            }
        }
    }
}
