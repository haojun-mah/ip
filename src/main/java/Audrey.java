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
        System.out.println("____________________________________________________________________");
        System.out.println("Hello! I'm Audrey");
        System.out.println("What can I do for you?");
        System.out.println(logo);
        System.out.println("____________________________________________________________________");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();        
        while (true) {
            if ("bye".equalsIgnoreCase(input)) {
                break;
            } else if ("list".equalsIgnoreCase(input)) {
                List toDoList = new List();
                print("To Do List Activated!");

                while (true) {
                    input = scanner.nextLine();
                    String[] processedInput = input.split(" "); // obtain first string of words before whitespace
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
                    } else {
                        print(toDoList.addToList(input));
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
