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
        taskStorage.add(createdTask);
        count++;
        
        // Assert: After adding, count should be consistent with storage size
        assert count == taskStorage.size() : "Count should match storage size after adding task";
        assert count > 0 : "Count should be positive after adding a task";
        
        return String.format("Got it. I've added this task:\n   %s\nNow you have %s tasks in the list.",
                                        createdTask.toString(), count);
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
            taskStorage.add(createdTask);
            count++;
            
            // Assert: After adding, count should be consistent with storage size
            assert count == taskStorage.size() : "Count should match storage size after adding deadline";
            
            return String.format("Got it. I've added this task:\n   %s\nNow you have %s tasks in the list.",
                                            createdTask.toString(), count);
        } catch (MissingDeadlineException e) {
            return e.getMessage();
        } catch (DateTimeParseException e) {
            return "Invalid Format for date. Enter: YYYY-MM-DD . E.g.: 2018-03-07";
        } catch (UnsupportedTemporalTypeException e) {
            return "Invalid time date";
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
            taskStorage.add(createdTask);
            count++;
            
            // Assert: After adding, count should be consistent with storage size
            assert count == taskStorage.size() : "Count should match storage size after adding event";
            
            return String.format("Got it. I've added this task:\n   %s\nNow you have %s tasks in the list.",
                                            createdTask.toString(), count);
        } catch (MissingEventException e) {
            return e.getMessage();
        } catch (WrongFromToOrientationException e) {
            return e.getMessage();
        } catch (DateTimeParseException e) {
            return "Invalid Format for date. Enter: YYYY-MM-DD . E.g.: 2018-03-07";
        } catch (UnsupportedTemporalTypeException e) {
            return "Invalid time date";
        }
    }

    /**
     * List out all tasks
     *
     * @return message with all tasks listed
     */
    public String showList() {
        return "Here are the tasks in your list:\n" + toString();

    }

    /**
     * Set specific task as marked
     *
     * @param task index the task is at in the ArrayList
     * @return message confirming task is marked
     */
    public String markTask(int task) {
        // Assert: Task index should be valid (1-based index)
        assert task > 0 : "Task index should be positive (1-based indexing)";
        
        int correctedTaskIndex = task - 1;
        if (correctedTaskIndex >= count || task <= 0) {
            return "Task does not exist!";
        }
        
        // Assert: Corrected index should be within bounds
        assert correctedTaskIndex >= 0 && correctedTaskIndex < taskStorage.size() : 
            "Corrected task index should be within storage bounds";
        
        taskStorage.get(correctedTaskIndex).markTask();
        return String.format("Nice! I've marked this task as done!:\n   %s", taskStorage.get(correctedTaskIndex));
    }

    /**
     * Set specific task as unmarked
     *
     * @param task index the task is at in the ArrayList
     * @return message confirming task is unmarked
     */
    public String unmarkTask(int task) {
        // Assert: Task index should be valid (1-based index)
        assert task > 0 : "Task index should be positive (1-based indexing)";
        
        int correctedTaskIndex = task - 1;
        if (correctedTaskIndex >= count || task <= 0) {
            return "Task does not exist!";
        }
        
        // Assert: Corrected index should be within bounds
        assert correctedTaskIndex >= 0 && correctedTaskIndex < taskStorage.size() : 
            "Corrected task index should be within storage bounds";
        
        taskStorage.get(correctedTaskIndex).unmarkTask();
        return String.format("Ok! I've marked this task as not done yet!:\n   %s", taskStorage.get(correctedTaskIndex));
    }

    /**
     * Delete specific task from list
     *
     * @param task index the task is at in the ArrayList
     * @return message confirming specifc task is deleted
     */
    public String delete(int task) {
        // Assert: Task index should be valid (1-based index)
        assert task > 0 : "Task index should be positive (1-based indexing)";
        
        int correctedTaskIndex = task - 1;
        if (correctedTaskIndex >= count || task <= 0) {
            return "Task does not exist!";
        }
        
        // Assert: Corrected index should be within bounds
        assert correctedTaskIndex >= 0 && correctedTaskIndex < taskStorage.size() : 
            "Corrected task index should be within storage bounds";
        
        String output = String.format("Removing this task!\n %s\nNow you have %s task in your list!",
                                        taskStorage.get(correctedTaskIndex), count);
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
