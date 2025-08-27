package components;

import java.util.ArrayList;

/**
 * List object manages and hold task objects
 */
public class List {
    private final ArrayList<Task> taskStorage;
    private int count;

    public List() {
        taskStorage = new ArrayList<Task>(); 
        count = 0;
    }

    /**
     * Create todo task
     * @param task task description
     * @return task created message
     */
    public String addToDos(String task) {
        Task createdTask = new Todo(task);
        taskStorage.add(createdTask);
        count++;
        return String.format("Got it. I've added this task:\n   %s\nNow you have %s tasks in the list.", createdTask.toString(), count);
    }

    /**
     * Creates deadline task
     * @param task task description
     * @return task created message
     */
    public String addDeadline(String task) {
        try {
            Task createdTask = new Deadline(task);
            taskStorage.add(createdTask);
            count++;
            return String.format("Got it. I've added this task:\n   %s\nNow you have %s tasks in the list.", createdTask.toString(), count);
        } catch (MissingDeadlineException e) {
            return e.getMessage();
        }
    }

    /**
     * Creates event task
     * @param task task description
     * @return task created message
     */
    public String addEvent(String task) {
        try {
            Task createdTask = new Event(task);
            taskStorage.add(createdTask);
            count++;
            return String.format("Got it. I've added this task:\n   %s\nNow you have %s tasks in the list.", createdTask.toString(), count);
        } catch (MissingEventException e) {
            return e.getMessage();
        }
    }

    /**
     * List out all tasks
     * @return message with all tasks listed
     */
    public String showList() {
        return "Here are the tasks in your list:\n" + toString();
     
    }

    /**
     * Set specific task as marked
     * @param task index the task is at in the ArrayList
     * @return message confirming task is marked
     */
    public String markTask(int task) { 
        int correctedTaskIndex = task - 1;
        if (correctedTaskIndex >= count || task <= 0) {
            return "Task does not exist!";
        }
        taskStorage.get(correctedTaskIndex).markTask();
        return String.format("Nice! I've marked this task as done!:\n   %s", taskStorage.get(correctedTaskIndex));
    }

    /**
     * Set specific task as unmarked
     * @param task index the task is at in the ArrayList
     * @return message confirming task is unmarked
     */
    public String unmarkTask(int task) {
        int correctedTaskIndex = task - 1;
        if (correctedTaskIndex >= count || task <= 0) {
            return "Task does not exist!";
        }
        taskStorage.get(correctedTaskIndex).unmarkTask();
        return String.format("Ok! I've marked this task as not done yet!:\n   %s", taskStorage.get(correctedTaskIndex));
    }

    /**
     * Delete specific task from list
     * @param task index the task is at in the ArrayList
     * @return message confirming specifc task is deleted
     */
    public String delete(int task) {
        int correctedTaskIndex = task - 1;
        if (correctedTaskIndex >= count || task <= 0) {
            return "Task does not exist!";
        }
        String output = String.format("Removing this task!\n %s\nNow you have %s task in your list!", taskStorage.get(correctedTaskIndex), count);
        taskStorage.remove(correctedTaskIndex);
        count--;
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
     * @return number of tasks
     */
    public int size() {
        return count;
    }

    /**
     * Get a task at specific index
     * @param index index of task (0-based)
     * @return Task object at index
     */
    public Task getTask(int index) {
        if (index >= 0 && index < count) {
            return taskStorage.get(index);
        }
        return null;
    }
}
 
