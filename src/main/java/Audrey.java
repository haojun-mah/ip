import java.util.Scanner;
import components.List;
public class Audrey {
    private static void print(String string) {
        System.out.println("    ____________________________________________________________________");
        System.out.println("    " + string);
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
                input = scanner.nextLine();
                while (true) {
                    if ("bye".equalsIgnoreCase(input)) {
                        break;
                    } else if ("list".equalsIgnoreCase(input)) {
                        print(toDoList.showList());
                        input = scanner.nextLine();
                    } else {
                        print(toDoList.addToList(input));
                        input = scanner.nextLine();
                    }
                }
                
            } else {
                print(input);
                input = scanner.nextLine();
            }
        }
        scanner.close();
        print("Bye! Hope to see you again!");
    }
}
