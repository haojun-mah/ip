import components.List;
import java.util.Scanner;
public class Audrey {
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
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();        
        while (true) {
            if ("bye".equalsIgnoreCase(input)) {
                break;
            } else if ("list".equalsIgnoreCase(input)) { // list mode
                List toDoList = new List();
                print("To Do List Activated!");

                while (true) {
                    input = scanner.nextLine();
                    String[] processedInput = input.split(" ", 2); // obtain first string of words before whitespace
                    String detectMark = processedInput[0];
                    if ("bye".equalsIgnoreCase(detectMark)) {
                        break;
                    } else if ("list".equalsIgnoreCase(input)) {
                        print(toDoList.showList());
                        input = scanner.nextLine();
                    } else if ("mark".equalsIgnoreCase(detectMark)) {
                        try {
                            print(toDoList.markTask(Integer.parseInt(processedInput[1])));
                        } catch (NumberFormatException e) {
                            print("Number not provided!");
                        }
                    } else if ("unmark".equalsIgnoreCase(detectMark)) {
                        try {
                            print(toDoList.unmarkTask(Integer.parseInt(processedInput[1])));
                        } catch (NumberFormatException e) {
                            print("Number not provided!");
                        }
                    } else if ("todo".equalsIgnoreCase(detectMark)){
                        print(toDoList.addToDos(processedInput[1]));
                    } else if ("deadline".equalsIgnoreCase(detectMark)){
                        print(toDoList.addDeadline(processedInput[1]));
                    } else if ("event".equalsIgnoreCase(detectMark)){
                        print(toDoList.addEvent(processedInput[1]));
                    } else if ("delete".equalsIgnoreCase(detectMark)) {
                        try {
                            print(toDoList.delete(Integer.parseInt(processedInput[1])));
                        } catch (NumberFormatException e) {
                            print("Number not provided!");
                        }
                    }
                        else {
                        print("Invalid Task add");
                    }


                }
                print("To Do List Deactivated");
            } else {
                print(input);
                input = scanner.nextLine();
            }
        }
        scanner.close();
        print("Bye! Hope to see you again!");
    }
}
