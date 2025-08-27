import components.Command;
import components.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    try {
                        // Regex patterns for different task types
                        String todoPattern = "\\[T\\]\\[([X ])\\]\\s*(.+)";
                        String deadlinePattern = "\\[D\\]\\[([X ])\\]\\s*(.+?)\\s*\\(by:\\s*(.+?)\\)";
                        String eventPattern = "\\[E\\]\\[([X ])\\]\\s*(.+?)\\s*\\(from:\\s*(.+?)\\s+to:\\s*(.+?)\\)";
                        
                        if (line.substring(2).matches(todoPattern)) {
                            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(todoPattern).matcher(line);
                            if (matcher.find()) {
                                String status = matcher.group(1);
                                String task = matcher.group(2);
                                toDoList.addToDos(task);
                                if ("X".equals(status)) {
                                    toDoList.markTask(toDoList.size());
                                }
                            }
                        } else if (line.substring(2).matches(deadlinePattern)) {
                            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(deadlinePattern).matcher(line);
                            if (matcher.find()) {
                                String status = matcher.group(1);
                                String task = matcher.group(2);
                                String deadline = matcher.group(3);
                                toDoList.addDeadline(task + " /by " + deadline);
                                if ("X".equals(status)) {
                                    toDoList.markTask(toDoList.size());
                                }
                            }
                        } else if (line.substring(2).matches(eventPattern)) {
                            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(eventPattern).matcher(line);
                            if (matcher.find()) {
                                String status = matcher.group(1);
                                String task = matcher.group(2);
                                String from = matcher.group(3);
                                String to = matcher.group(4);
                                toDoList.addEvent(task + " /from " + from + " /to " + to);
                                if ("X".equals(status)) {
                                    toDoList.markTask(toDoList.size());
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing line: " + line);
                    }
                }
                fileScanner.close();
            } else {
                // Create new file
                audreyDB.createNewFile();
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
                input = scanner.nextLine();
            }
        }
        } finally {
            try {
                FileWriter fw = new FileWriter(FILE_PATH); // This will overwrite
                if (fw != null) {
                    fw.write(toDoList.toString());
                    fw.close();
                }
           } catch (IOException e) {
                print(e.getMessage());
            } 
            print("Bye! Hope to see you again!");
        }
    } 
} 
