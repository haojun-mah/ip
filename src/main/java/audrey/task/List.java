package audrey.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.ArrayList;

import audrey.exception.MissingDeadlineException;
import audrey.exception.MissingEventException;
import audrey.exception.WrongFromToOrientationException;

/** List object manages and hold task objects */
public class List {
    private static final String TASK_ADDED_FORMAT =
            "Got it. I've added this task:\n   %s\n" + "Now you have %s tasks in the list.";
    private static final String TASK_MARKED_FORMAT = "Nice! I've marked this task as done!:\n   %s";
    private static final String TASK_UNMARKED_FORMAT =
            "Ok! I've marked this task as not done yet!:\n   %s";
    private static final String TASK_DELETED_FORMAT =
            "Removing this task!\n %s\nNow you have %s task in your list!";
    private static final String TASK_NOT_EXIST_MSG = "Task does not exist!";
    private static final String INVALID_DATE_FORMAT_MSG =
            "Invalid Format for date. " + "Enter: YYYY-MM-DD . E.g.: 2018-03-07";
    private static final String INVALID_TIME_DATE_MSG = "Invalid time date";
    private static final String LIST_HEADER = "Here are the tasks in your list:\n";
    private static final String SNOOZE_HEADER = "Here are the tasks you can snooze:\n";
    private static final String TASK_SNOOZED_FOREVER_FORMAT = "Task snoozed forever:\n   %s";
    private static final String TASK_SNOOZED_UNTIL_FORMAT = "Task snoozed until %s:\n   %s";
    private static final String NO_TASKS_TO_SNOOZE_MSG = "No tasks available to snooze!";
    private static final String TASK_REACTIVATED_MSG = "Task reactivated:\n   %s";

    private final ArrayList<Task> taskStorage;
    private int count;

    /** Constructor for List class. Initializes an empty task storage. */
    public List() {
        taskStorage = new ArrayList<>();
        count = 0;
    }

    /**
     * Create todo task
     *
     * @param task task description
     * @return task created message
     */
    public String addToDos(String task) {
        assert task != null : "Task description cannot be null";

        Task createdTask = new Todo(task);
        return addTaskToList(createdTask);
    }

    /**
     * Creates deadline task
     *
     * @param task task description
     * @return task created message
     */
    public String addDeadline(String task) {
        assert task != null : "Task description cannot be null";

        try {
            Task createdTask = new Deadline(task);
            return addTaskToList(createdTask);
        } catch (MissingDeadlineException e) {
            return e.getMessage();
        } catch (DateTimeParseException e) {
            return INVALID_DATE_FORMAT_MSG;
        } catch (UnsupportedTemporalTypeException e) {
            return INVALID_TIME_DATE_MSG;
        }
    }

    /**
     * Creates event task
     *
     * @param task task description
     * @return task created message
     */
    public String addEvent(String task) {
        assert task != null : "Task description cannot be null";

        try {
            Task createdTask = new Event(task);
            return addTaskToList(createdTask);
        } catch (MissingEventException e) {
            return e.getMessage();
        } catch (WrongFromToOrientationException e) {
            return e.getMessage();
        } catch (DateTimeParseException e) {
            return INVALID_DATE_FORMAT_MSG;
        } catch (UnsupportedTemporalTypeException e) {
            return INVALID_TIME_DATE_MSG;
        }
    }

    /**
     * Helper method to add a task to the list and return success message.
     *
     * @param task Task to be added
     * @return Success message with task details
     */
    private String addTaskToList(Task task) {
        taskStorage.add(task);
        count++;

        // Assert: After adding, count should be consistent with storage size
        assert count == taskStorage.size() : "Count should match storage size after adding task";
        assert count > 0 : "Count should be positive after adding a task";

        return String.format(TASK_ADDED_FORMAT, task.toString(), count);
    }

    /**
     * Validates if the given task index is within valid bounds (1-based indexing).
     *
     * @param taskIndex The task index to validate (1-based)
     * @return true if the index is valid, false otherwise
     */
    private boolean isValidTaskIndex(int taskIndex) {
        // Assert: Task index should be positive for 1-based indexing
        assert taskIndex > 0 : "Task index should be positive (1-based indexing)";

        int correctedTaskIndex = taskIndex - 1;
        boolean isValid = correctedTaskIndex >= 0 && correctedTaskIndex < count;

        // Assert: If valid, corrected index should be within storage bounds
        if (isValid) {
            assert correctedTaskIndex < taskStorage.size()
                    : "Corrected task index should be within storage bounds";
        }

        return isValid;
    }

    /**
     * List out all active (non-snoozed) tasks
     *
     * @return message with all active tasks listed
     */
    public String showList() {
        return LIST_HEADER + toStringActiveTasks();
    }

    /**
     * List out all tasks including snoozed ones (for internal use)
     *
     * @return message with all tasks listed
     */
    public String showAllTasks() {
        return LIST_HEADER + toString();
    }

    /**
     * Set specific task as marked
     *
     * @param task index the task is at in the ArrayList
     * @return message confirming task is marked
     */
    public String markTask(int task) {
        if (!isValidTaskIndex(task)) {
            return TASK_NOT_EXIST_MSG;
        }

        int correctedTaskIndex = task - 1;
        taskStorage.get(correctedTaskIndex).markTask();
        return String.format(TASK_MARKED_FORMAT, taskStorage.get(correctedTaskIndex));
    }

    /**
     * Set specific task as unmarked
     *
     * @param task index the task is at in the ArrayList
     * @return message confirming task is unmarked
     */
    public String unmarkTask(int task) {
        if (!isValidTaskIndex(task)) {
            return TASK_NOT_EXIST_MSG;
        }

        int correctedTaskIndex = task - 1;
        taskStorage.get(correctedTaskIndex).unmarkTask();
        return String.format(TASK_UNMARKED_FORMAT, taskStorage.get(correctedTaskIndex));
    }

    /**
     * Delete specific task from list
     *
     * @param task index the task is at in the ArrayList
     * @return message confirming specifc task is deleted
     */
    public String delete(int task) {
        if (!isValidTaskIndex(task)) {
            return TASK_NOT_EXIST_MSG;
        }

        int correctedTaskIndex = task - 1;
        String output =
                String.format(TASK_DELETED_FORMAT, taskStorage.get(correctedTaskIndex), count - 1);
        taskStorage.remove(correctedTaskIndex);
        count--;

        // Assert: After deletion, count should be consistent with storage size
        assert count == taskStorage.size() : "Count should match storage size after deletion";
        assert count >= 0 : "Count should not be negative after deletion";

        return output;
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < count; i++) {
            output += String.format("%s.%s\n", i + 1, taskStorage.get(i));
        }
        return output;
    }

    /**
     * Returns string representation of only active (non-snoozed) tasks.
     *
     * @return String with active tasks formatted with their display numbers
     */
    public String toStringActiveTasks() {
        StringBuilder output = new StringBuilder();
        int displayIndex = 1;

        for (int i = 0; i < count; i++) {
            Task task = taskStorage.get(i);
            if (!task.isSnoozed()) {
                output.append(String.format("%d.%s\n", displayIndex, task));
                displayIndex++;
            }
        }

        if (displayIndex == 1) {
            return "No active tasks!";
        }

        return output.toString();
    }

    /**
     * Get the number of tasks in the list
     *
     * @return number of tasks
     */
    public int size() {
        return count;
    }

    /**
     * Get a task at specific index
     *
     * @param index index of task (0-based)
     * @return Task object at index
     */
    public Task getTask(int index) {
        // Assert: Index should be non-negative for 0-based indexing
        assert index >= 0 : "Task index should be non-negative (0-based indexing)";

        if (index >= 0 && index < count) {
            // Assert: If index is valid, it should also be within storage bounds
            assert index < taskStorage.size() : "Index should be within storage bounds";
            return taskStorage.get(index);
        }
        return null;
    }

    /**
     * Returns specific tasks which matches with given regex
     *
     * @param task Target task characters
     * @return ArrayList containing matched tasks
     */
    public ArrayList<Task> findTasks(String task) {
        // Assert: Search parameter should not be null
        assert task != null : "Search string cannot be null";

        ArrayList<Task> output = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Assert: Loop index should be within bounds
            assert i >= 0 && i < taskStorage.size() : "Loop index should be within storage bounds";

            Task targetTask = taskStorage.get(i);
            if (targetTask.toString().contains(task)) {
                output.add(targetTask);
            }
        }

        // Assert: Output should not be null
        assert output != null : "Find result should not be null";

        return output;
    }

    /**
     * Shows the list of tasks that can be snoozed (non-completed tasks). Uses the same numbering
     * system as the regular task list.
     *
     * @return String containing the list of snoozable tasks
     */
    public String showSnoozableTasks() {
        StringBuilder output = new StringBuilder(SNOOZE_HEADER);
        boolean hasFoundAny = false;

        for (int i = 0; i < count; i++) {
            Task task = taskStorage.get(i);
            if (!task.isCompleted()) { // Only show non-completed tasks
                hasFoundAny = true;
                String snoozeStatus = "";
                if (task.isSnoozed()) {
                    if (task.isSnoozedForever()) {
                        snoozeStatus = " [SNOOZED FOREVER]";
                    } else {
                        snoozeStatus = " [SNOOZED UNTIL " + task.getSnoozeUntil() + "]";
                    }
                }
                // Remove snooze info from task toString and add our own formatted version
                String taskStr = task.toString();
                if (task.isSnoozed()) {
                    // Remove the snooze info that's already in the toString
                    taskStr = taskStr.replaceAll(" \\(snoozed.*?\\)", "");
                }
                output.append(String.format("%d.%s%s\n", i + 1, taskStr, snoozeStatus));
            }
        }

        if (!hasFoundAny) {
            return NO_TASKS_TO_SNOOZE_MSG;
        }

        output.append("\nCommands: 'snooze [number]' (forever), ")
                .append("'snooze [number] YYYY-MM-DD' (until date), ")
                .append("'unsnooze [number]' (remove snooze)");

        return output.toString();
    }

    /**
     * Snoozes a task forever.
     *
     * @param taskIndex 1-based index of the task to snooze
     * @return Message indicating the result of the snooze operation
     */
    public String snoozeTaskForever(int taskIndex) {
        if (!isValidTaskIndex(taskIndex)) {
            return TASK_NOT_EXIST_MSG;
        }

        int correctedIndex = taskIndex - 1;
        Task task = taskStorage.get(correctedIndex);

        if (task.isCompleted()) { // Check if task is completed
            return "Cannot snooze a completed task!";
        }

        // Allow overwriting existing snooze values
        if (task.isSnoozed()) {
            if (task.isSnoozedForever()) {
                return "Task is already snoozed forever!";
            } else {
                task.snoozeForever();
                return String.format("Task snooze changed from date to forever:\n   %s", task);
            }
        }

        task.snoozeForever();
        return String.format(TASK_SNOOZED_FOREVER_FORMAT, task);
    }

    /**
     * Snoozes a task until a specific date.
     *
     * @param taskIndex 1-based index of the task to snooze
     * @param dateString Date string in YYYY-MM-DD format
     * @return Message indicating the result of the snooze operation
     */
    public String snoozeTaskUntil(int taskIndex, String dateString) {
        if (!isValidTaskIndex(taskIndex)) {
            return TASK_NOT_EXIST_MSG;
        }

        try {
            LocalDate snoozeDate = LocalDate.parse(dateString);

            int correctedIndex = taskIndex - 1;
            Task task = taskStorage.get(correctedIndex);

            if (task.isCompleted()) { // Check if task is completed
                return "Cannot snooze a completed task!";
            }

            // Allow overwriting existing snooze values
            if (task.isSnoozed()) {
                if (task.isSnoozedForever()) {
                    task.snooze(snoozeDate);
                    return String.format(
                            "Task was snoozed forever, now changed to snooze until %s:\n   %s",
                            snoozeDate, task);
                } else {
                    task.snooze(snoozeDate);
                    return String.format(
                            "Task snooze date updated to %s:\n   %s", snoozeDate, task);
                }
            }

            task.snooze(snoozeDate);
            return String.format(TASK_SNOOZED_UNTIL_FORMAT, snoozeDate, task);

        } catch (DateTimeParseException e) {
            return INVALID_DATE_FORMAT_MSG;
        }
    }

    /**
     * Unsnoozes a task (removes snooze status).
     *
     * @param taskIndex 1-based index of the task to unsnooze
     * @return Message indicating the result of the unsnooze operation
     */
    public String unsnoozeTask(int taskIndex) {
        if (!isValidTaskIndex(taskIndex)) {
            return TASK_NOT_EXIST_MSG;
        }

        int correctedIndex = taskIndex - 1;
        Task task = taskStorage.get(correctedIndex);

        if (!task.isSnoozed()) {
            return "Task is not snoozed!";
        }

        task.unsnooze();
        return String.format("Task unsnoozed:\n   %s", task);
    }

    /**
     * Gets the active (non-snoozed or expired snooze) tasks for display.
     *
     * @return ArrayList of active tasks
     */
    public ArrayList<Task> getActiveTasks() {
        ArrayList<Task> activeTasks = new ArrayList<>();
        for (Task task : taskStorage) {
            if (!task.isSnoozed()) {
                activeTasks.add(task);
            }
        }
        return activeTasks;
    }
}
