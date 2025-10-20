package audrey.command;

/** Enum mapping user-entered command strings to strongly-typed command identifiers. */
public enum Command {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete"),
    FIND("find"),
    SNOOZE("snooze"),
    UNSNOOZE("unsnooze"),
    HELP("help");

    private final String commandString;

    Command(String commandString) {
        this.commandString = commandString;
    }

    /**
     * Resolves a string to its corresponding command enum.
     *
     * @param input command token from user input
     * @return matching command, or {@code null} if no match is found
     */
    public static Command fromString(String input) {
        for (Command cmd : Command.values()) {
            if (cmd.commandString.equalsIgnoreCase(input)) {
                return cmd;
            }
        }
        return null; // Invalid command
    }

    /** @return canonical string version of the command */
    public String getCommandString() {
        return commandString;
    }
}
