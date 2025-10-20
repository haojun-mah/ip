package audrey.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import audrey.task.List;

/** Handles parsing of event task lines from storage. */
public class EventParser extends BaseStorageOperation {

    /**
     * Builds a parser that reconstructs event tasks from stored lines.
     *
     * @param toDoList task list to populate
     */
    public EventParser(List toDoList) {
        super(toDoList);
    }

    @Override
    public void execute(String line) {
        parseEventLine(line);
    }

    /**
     * Checks if a line represents an event task.
     *
     * @param line The line to check
     * @return true if line is an event task
     */
    public static boolean isEventLine(String line) {
        return line.matches(EVENT_PATTERN);
    }

    /**
     * Parses an event task line and adds it to the task list.
     *
     * @param line The event line to parse
     */
    private void parseEventLine(String line) {
        Matcher matcher = Pattern.compile(EVENT_PATTERN).matcher(line);

        if (!matcher.find()) {
            throw new IllegalArgumentException(
                    "Event line does not match expected pattern: " + line);
        }

        String status = matcher.group(1);
        String task = matcher.group(2);
        String from = matcher.group(3);
        String to = matcher.group(4);

        validateTaskDescription(task, "Event");
        validateTaskStatus(status, "Event");
        validateDateFormat(from, "Event start");
        validateDateFormat(to, "Event end");
        validateEventDates(from.trim(), to.trim());

        addEventToList(task.trim(), from.trim(), to.trim(), status);
    }

    /**
     * Validates that event dates are in correct order.
     *
     * @param from The start date
     * @param to The end date
     */
    private void validateEventDates(String from, String to) {
        try {
            LocalDate startDate = LocalDate.parse(from);
            LocalDate endDate = LocalDate.parse(to);

            if (endDate.isBefore(startDate)) {
                throw new IllegalArgumentException(
                        "Event end date (" + to + ") cannot be before start date (" + from + ")");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Warning: Could not validate date order due to parsing error");
        }
    }

    /**
     * Adds an event task to the list with the specified status.
     *
     * @param task The task description
     * @param from The start date
     * @param to The end date
     * @param status The completion status
     */
    private void addEventToList(String task, String from, String to, String status) {
        try {
            toDoList.addEvent(task + " /from " + from + " /to " + to);
            if ("X".equals(status)) {
                toDoList.markTask(toDoList.size());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add event task: " + task, e);
        }
    }
}
