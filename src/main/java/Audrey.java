import java.util.Scanner;
public class Audrey {
    public static void main(String[] args) {
        String logo = "\n" +
                "  #####  ##   ## #####  ##### ####### ##   ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##      ##  ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##       ## ##\n" +
                " ####### ##   ## #####  #####  #####     ### \n" +
                " ##   ## ##   ## ##  ## ##  ## ##          ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##          ##\n" +
                " ##   ##  #####  ##  ## ##  ## #######     ##\n";
        
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
            } else {
                System.out.println("    ____________________________________________________________________");
                System.out.println("    " + input);
                System.out.println("    ____________________________________________________________________");
                input = scanner.nextLine();
            }
        }
        scanner.close();
        System.out.println("____________________________________________________________________");
        System.out.println("Bye. Hope to see you again!");
        System.out.println("____________________________________________________________________");
    }
}
