package audrey.task;

import java.time.format.DateTimeParseException;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.ArrayList;

import audrey.exception.MissingDeadlineException;
import audrey.exception.MissingEventException;
import audrey.exception.WrongFromToOrientationException;

/**
 * List object manages and hold task objects
 */
public class List {
    private static final String TASK_ADDED_FORMAT = "Got it. I've added this task:\n   %s\nNow you have %s tasks in the list.";
    private static final String TASK_MARKED_FORMAT = "Nice! I've marked this task as done!:\n   %s";
    private static final String TASK_UNMARKED_FORMAT = "Ok! I've marked this task as not done yet!:\n   %s";
    private static final String TASK_DELETED_FORMAT = "Removing this task!\n %s\nNow you have %s task in your list!";
    private static final String TASK_NOT_EXIST_MSG = "Task does not exist!";
    private static final String INVALID_DATE_FORMAT_MSG = "Invalid Format for date. Enter: YYYY-MM-DD . E.g.: 2018-03-07";
    private static final String INVALID_TIME_DATE_MSG = "Invalid time date";
    private static final String LIST_HEADER = "Here are the tasks in your list:\n";

    private final ArrayList<Task> taskStorage;
    private int count;

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
        // Assert: Task parameter should not be null
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
        // Assert: Task parameter should not be null
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
        // Assert: Task parameter should not be null
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
            assert correctedTaskIndex < taskStorage.size() : "Corrected task index should be within storage bounds";
        }

        return isValid;
    }

    /**
     * List out all tasks
     *
     * @return message with all tasks listed
     */
    public String showList() {
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
        String output = String.format(TASK_DELETED_FORMAT, taskStorage.get(correctedTaskIndex), count - 1);
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
     * @return ArrayList<Task> contains matched task
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
}
