package audrey.storage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import audrey.task.List;

/** Handles parsing of todo task lines from storage. */
public class TodoParser extends BaseStorageOperation {

    public TodoParser(List toDoList) {
        super(toDoList);
    }

    @Override
    public void execute(String line) {
        parseTodoLine(line);
    }

    /**
     * Checks if a line represents a todo task.
     *
     * @param line The line to check
     * @return true if line is a todo task
     */
    public static boolean isTodoLine(String line) {
        return line.matches(TODO_PATTERN);
    }

    /**
     * Parses a todo task line and adds it to the task list.
     *
     * @param line The todo line to parse
     */
    private void parseTodoLine(String line) {
        Matcher matcher = Pattern.compile(TODO_PATTERN).matcher(line);

        if (!matcher.find()) {
            throw new IllegalArgumentException(
                    "Todo line does not match expected pattern: " + line);
        }

        String status = matcher.group(1);
        String task = matcher.group(2);

        validateTaskDescription(task, "Todo");
        validateTaskStatus(status, "Todo");

        addTodoToList(task.trim(), status);
    }

    /**
     * Adds a todo task to the list with the specified status.
     *
     * @param task The task description
     * @param status The completion status
     */
    private void addTodoToList(String task, String status) {
        try {
            toDoList.addToDos(task);
            if ("X".equals(status)) {
                toDoList.markTask(toDoList.size());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add todo task: " + task, e);
        }
    }
}
