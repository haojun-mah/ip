import components.Command;
import components.List;

import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;

import java.io.IOException;
import javax.sound.sampled.Line;

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
        String FILE_PATH = "audrey_db.txt";
        FileWriter fw = null;
        File audreyDB = new File(FILE_PATH);
        List toDoList = new List();
        String logo = "\n" +
                "  #####  ##   ## #####  ##### ####### ##   ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##      ##  ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##       ## ##\n" +
                " ####### ##   ## ##  ## #####  #####     ### \n" +
                " ##   ## ##   ## ##  ## ##  ## ##          ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##          ##\n" +
                " ##   ##  #####  #####  ##  ## #######     ##\n";
        print("Hello! I'm Audrey\nWhat can I do for you!\n" + logo);

        try {
            if (audreyDB.exists()) {
                // Loads DB info into bot
                Scanner fileScanner = new Scanner(audreyDB);
                fw = new FileWriter(FILE_PATH);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine().trim();
                    if (line.startsWith("[T}]")) {
                        toDoList.addToDos(line);
                    } else if (line.startsWith("[D]")) {
                        toDoList.addDeadline(line);
                    } else if (line.startsWith("[E]")) {
                        toDoList.addEvent(line);
                    }
                }
            } else {
                // Create new file
                audreyDB.createNewFile();
                fw = new FileWriter(FILE_PATH);
            } 
        } catch (IOException e) {
            print(e.getMessage());
        } finally {
            print(toDoList.showList());
        }
        
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();        
            while (true) {
                // Terminates bot when input is bye
                if ("bye".equalsIgnoreCase(input)) {
                    break;
                } else if ("list".equalsIgnoreCase(input)) { 
                    // List Management Mode
                    print("To Do List Activated!");
                    
                    while (true) {
                        input = scanner.nextLine();
                        String[] processedInput = input.split(" ", 2); // obtain first string of words before whitespace
                        String detectMark = processedInput[0];
                        Command command = Command.fromString(detectMark);

                        if (command == null) {
                            print("Invalid Task");
                        } else if (command == Command.BYE) {
                            // Terminates List Mode
                            input = "";
                            print("To Do List Deactivated");
                            break;
                        } else {
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
                        }
                    } 
            } else {
                print(input);
                input = scanner.nextLine();
            }
        }
        } finally {
            print("Bye! Hope to see you again!");
        }
    } 
} 
