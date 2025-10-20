package audrey.storage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import audrey.task.List;

/** Handles parsing of deadline task lines from storage. */
public class DeadlineParser extends BaseStorageOperation {

    /**
     * Builds a parser that reconstructs deadline tasks from stored lines.
     *
     * @param toDoList task list to populate
     */
    public DeadlineParser(List toDoList) {
        super(toDoList);
    }

    @Override
    public void execute(String line) {
        parseDeadlineLine(line);
    }

    /**
     * Checks if a line represents a deadline task.
     *
     * @param line The line to check
     * @return true if line is a deadline task
     */
    public static boolean isDeadlineLine(String line) {
        return line.matches(DEADLINE_PATTERN);
    }

    /**
     * Parses a deadline task line and adds it to the task list.
     *
     * @param line The deadline line to parse
     */
    private void parseDeadlineLine(String line) {
        Matcher matcher = Pattern.compile(DEADLINE_PATTERN).matcher(line);

        if (!matcher.find()) {
            throw new IllegalArgumentException(
                    "Deadline line does not match expected pattern: " + line);
        }

        String status = matcher.group(1);
        String task = matcher.group(2);
        String deadline = matcher.group(3);

        validateTaskDescription(task, "Deadline");
        validateTaskStatus(status, "Deadline");
        validateDateFormat(deadline, "Deadline");

        addDeadlineToList(task.trim(), deadline.trim(), status);
    }

    /**
     * Adds a deadline task to the list with the specified status.
     *
     * @param task The task description
     * @param deadline The deadline date
     * @param status The completion status
     */
    private void addDeadlineToList(String task, String deadline, String status) {
        try {
            toDoList.addDeadline(task + " /by " + deadline);
            if ("X".equals(status)) {
                toDoList.markTask(toDoList.size());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add deadline task: " + task, e);
        }
    }
}
