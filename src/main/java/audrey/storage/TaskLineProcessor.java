package audrey.storage;

import audrey.task.List;

/** Processes task lines from storage files by delegating to appropriate parsers. */
public class TaskLineProcessor extends BaseStorageOperation {

    private final TodoParser todoParser;
    private final DeadlineParser deadlineParser;
    private final EventParser eventParser;

    public TaskLineProcessor(List toDoList) {
        super(toDoList);
        this.todoParser = new TodoParser(toDoList);
        this.deadlineParser = new DeadlineParser(toDoList);
        this.eventParser = new EventParser(toDoList);
    }

    @Override
    public void execute(String line) {
        parseTaskLine(line);
    }

    /**
     * Parses a task line by determining its type and delegating to the appropriate parser.
     *
     * @param line The line to parse
     */
    private void parseTaskLine(String line) {
        // Assert: Line should not be null or empty
        assert line != null : "Line to parse cannot be null";

        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            return; // Skip empty lines
        }

        // Validate line length
        if (trimmedLine.length() > MAX_LINE_LENGTH) {
            System.out.println(
                    "Warning: Line is very long ("
                            + trimmedLine.length()
                            + " characters): "
                            + trimmedLine.substring(0, Math.min(50, trimmedLine.length()))
                            + "...");
        }

        try {
            if (TodoParser.isTodoLine(trimmedLine)) {
                todoParser.execute(trimmedLine);
            } else if (DeadlineParser.isDeadlineLine(trimmedLine)) {
                deadlineParser.execute(trimmedLine);
            } else if (EventParser.isEventLine(trimmedLine)) {
                eventParser.execute(trimmedLine);
            } else {
                System.out.println(
                        "Warning: Unrecognized task format, skipping line: " + trimmedLine);
            }
        } catch (Exception e) {
            System.err.println("Error parsing line: " + trimmedLine);
            System.err.println("Error details: " + e.getMessage());
        }
    }
}
