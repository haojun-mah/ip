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
        // Assert: Database filename should be valid
        assert AUDREY_DB != null && !AUDREY_DB.trim().isEmpty() : "Database filename should be valid";

        this.instanceStorage = new Storage(AUDREY_DB);
        this.instanceToDoList = instanceStorage.getToDoList();
        this.instanceCommand = new Parser(instanceToDoList);

        // Assert: All instance variables should be properly initialized
        assert this.instanceStorage != null : "Instance storage should be properly initialized";
        assert this.instanceToDoList != null : "Instance todo list should be properly initialized";
        assert this.instanceCommand != null : "Instance command parser should be properly initialized";
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
        print("\n" + getHelpMessage());

    }

    /**
     * Returns a help message showing all available commands.
     *
     * @return String containing help information
     */
    private static String getHelpMessage() {
        return """
                Available Commands:
                ==================
                • list - Show all tasks
                • todo <description> - Add a todo task
                • deadline <description> /by <date> - Add a deadline task (date: YYYY-MM-DD)
                • event <description> /from <date> /to <date> - Add an event task
                • mark <number> - Mark task as done
                • unmark <number> - Mark task as not done
                • delete <number> - Delete a task
                • find <keyword> - Find tasks containing keyword
                • snooze <number> - Snooze task forever
                • snooze <number> <date> - Snooze task until date (YYYY-MM-DD)
                • unsnooze <number> - Remove snooze status from task
                • bye - Exit the application

                Examples:
                • todo Read book
                • deadline Submit assignment /by 2025-09-25
                • event Meeting /from 2025-09-20 /to 2025-09-20
                • snooze 1 2025-10-01
                """;
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
        // Assert: Input should not be null
        assert input != null : "User input cannot be null";
        // Assert: Static command should be initialized
        assert command != null : "Static command parser should be initialized";

        try {
            String response = command.runInput(input);

            // Assert: Response should not be null
            assert response != null : "Parser response should not be null";

            return response;
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
        // Assert: Input should not be null
        assert input != null : "User input cannot be null";
        // Assert: Instance command should be initialized
        assert instanceCommand != null : "Instance command parser should be initialized";

        try {
            String response = instanceCommand.runInput(input);

            // Assert: Response should not be null
            assert response != null : "Parser response should not be null";

            return response;
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
