package audrey.ui;

import audrey.command.Parser;
import audrey.storage.Storage;
import audrey.task.List;

/**
 * Contains logic for bot workflow.
 */
public class Audrey {
    private static final String AUDREY_DB = "audrey_db.txt";
    private static final Storage audreyStorage = new Storage(AUDREY_DB);
    private static final List toDoList = audreyStorage.getToDoList();
    private static final Parser command = new Parser(toDoList);

    // Instance variables for GUI mode
    private Storage instanceStorage;
    private List instanceToDoList;
    private Parser instanceCommand;

    /**
     * Default constructor for GUI usage.
     */
    public Audrey() {
        this.instanceStorage = new Storage(AUDREY_DB);
        this.instanceToDoList = instanceStorage.getToDoList();
        this.instanceCommand = new Parser(instanceToDoList);
    }

    /**
     * Entry point.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        String logo = """

                                          #####  ##   ## #####  ##### ####### ##   ##
                                         ##   ## ##   ## ##  ## ##  ## ##      ##  ##
                                         ##   ## ##   ## ##  ## ##  ## ##       ## ##
                                         ####### ##   ## ##  ## #####  #####     ###
                                         ##   ## ##   ## ##  ## ##  ## ##          ##
                                         ##   ## ##   ## ##  ## ##  ## ##          ##
                                         ##   ##  #####  #####  ##  ## #######     ##
                                        """;
        print("Hello! I'm Audrey\nWhat can I do for you!\n" + logo);
        print("Task that you have pending:\n" + toDoList.toString());

    }

    /**
     * Prettier print for CLI.
     *
     * @param string Text to print
     */
    private static void print(String string) {
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

    public static String getResponse(String input) {
        try {
            return command.runInput(input);
        } catch (Exception e) {
            print("Error with parser");
            return "Error with parser";
        }
    }

    /**
     * Instance method for GUI to get response.
     *
     * @param input User input string
     * @return Response string
     */
    public String getInstanceResponse(String input) {
        try {
            return instanceCommand.runInput(input);
        } catch (Exception e) {
            return "Error with parser";
        }
    }

    public static void shutdown() {
        audreyStorage.saveToFile();
    }

    /**
     * Instance method for GUI to shutdown.
     */
    public void instanceShutdown() {
        instanceStorage.saveToFile();
    }

}
