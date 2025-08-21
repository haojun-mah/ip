package components;

public enum Command {
    BYE("bye"),
    LIST("list"), 
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete");
    
    private final String commandString;
    
    Command(String commandString) {
        this.commandString = commandString;
    }
    
    public static Command fromString(String input) {
        for (Command cmd : Command.values()) {
            if (cmd.commandString.equalsIgnoreCase(input)) {
                return cmd;
            }
        }
        return null; // Invalid command
    }
    
    public String getCommandString() {
        return commandString;
    }
}
