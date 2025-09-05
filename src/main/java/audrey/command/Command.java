package audrey.command;

/**
 * Enum which maps command for easier use in Audrey
 */
public enum Command {
    BYE("bye"), LIST("list"), MARK("mark"), UNMARK("unmark"), TODO("todo"), DEADLINE("deadline"), EVENT(
                                    "event"), DELETE("delete"), FIND("find");

    private final String commandString;

    Command(String commandString) {
        this.commandString = commandString;
    }

    /**
     * Matches command with enum
     *
     * @param input command
     * @return command matched
     */
    public static Command fromString(String input) {
        for (Command cmd : Command.values()) {
            if (cmd.commandString.equalsIgnoreCase(input)) {
                return cmd;
            }
        }
        return null; // Invalid command
    }

    /**
     * Returns command which is mapped.
     *
     * @return String command
     */
    public String getCommandString() {
        return commandString;
    }
}
