import components.List;
import components.Command;
import java.util.Scanner;
import javax.security.auth.x500.X500Principal;

/**
 * Contains logic for bot workflow
 * 
*/
public class Audrey {
    /**
     * Print function which adds indentation and seperation line for cleaner
     * response code
     * 
     * @param string String to be printed
     */
    private static void print(String string) {
        String[] splitString = string.split("\n");
        String formattedString = "";
        for (int i = 0; i < splitString.length; i++) {
            if (i+1 == splitString.length) {
                formattedString += "    " + splitString[i];
            } else {
                formattedString += "    " + splitString[i] + '\n';
            }
        }
        System.out.println("    ____________________________________________________________________");
        System.out.println(formattedString);
        System.out.println("    ____________________________________________________________________");
    }
    /**
     * Entry point for code
     * @param args
     */
    public static void main(String[] args) {
        String logo = "\n" +
                "  #####  ##   ## #####  ##### ####### ##   ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##      ##  ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##       ## ##\n" +
                " ####### ##   ## #####  #####  #####     ### \n" +
                " ##   ## ##   ## ##  ## ##  ## ##          ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##          ##\n" +
                " ##   ##  #####  ##  ## ##  ## #######     ##\n";
        print("Hello! I'm Audrey\nWhat can I do for you!\n" + logo);
        
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();        
            while (true) {
                // Terminates bot when input is bye
                if ("bye".equalsIgnoreCase(input)) {
                    break;
                } else if ("list".equalsIgnoreCase(input)) { 
                    // List Management Mode
                    List toDoList = new List();
                    print("To Do List Activated!");

                    while (true) {
                        input = scanner.nextLine();
                        String[] processedInput = input.split(" ", 2); // obtain first string of words before whitespace
                        String detectMark = processedInput[0];
                        
                        Command command = Command.fromString(detectMark);
                        if (command == null) {
                            print("Invalid Task");
                            continue;
                        }
                        
                        switch (command) {
                            case BYE:
                                break; // This will break out of the while loop
                            case LIST:
                                print(toDoList.showList());
                                break;
                            case MARK:
                                try {
                                    print(toDoList.markTask(Integer.parseInt(processedInput[1])));
                                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                                    print("Number not provided!");
                                }
                                break;
                            case UNMARK:
                                try {
                                    print(toDoList.unmarkTask(Integer.parseInt(processedInput[1])));
                                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                                    print("Number not provided!");
                                }
                                break;
                            case TODO:
                                if (processedInput.length > 1) {
                                    print(toDoList.addToDos(processedInput[1]));
                                } else {
                                    print("Todo description cannot be empty!");
                                }
                                break;
                            case DEADLINE:
                                if (processedInput.length > 1) {
                                    print(toDoList.addDeadline(processedInput[1]));
                                } else {
                                    print("Deadline description cannot be empty!");
                                }
                                break;
                            case EVENT:
                                if (processedInput.length > 1) {
                                    print(toDoList.addEvent(processedInput[1]));
                                } else {
                                    print("Event description cannot be empty!");
                                }
                                break;
                            case DELETE:
                                try {
                                    print(toDoList.delete(Integer.parseInt(processedInput[1])));
                                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                                    print("Number not provided!");
                                }
                                break;
                            }
                    // Terminates List Mode
                    if (command == Command.BYE) {
                        break;
                    }
                }
                print("To Do List Deactivated");
            } else {
                print(input);
                input = scanner.nextLine();
            }
        }
        
        print("Bye! Hope to see you again!");
        } 
    } 
} 
