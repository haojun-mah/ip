package components;

public class List {
    private final Task[] taskStorage;
    private int count;

    public List() {
        taskStorage = new Task[100]; 
        count = 0;
    }

    public String addToDos(String task) {
        Task createdTask = new Todo(task);
        taskStorage[count] = createdTask;
        count++;
        return String.format("Got it. I've added this task:\n   %s\nNow you have %s tasks in the list.", createdTask.toString(), count);
    }

    public String addDeadline(String task) {
        try {
            Task createdTask = new Deadline(task);
            taskStorage[count] = createdTask;
            count++;
            return String.format("Got it. I've added this task:\n   %s\nNow you have %s tasks in the list.", createdTask.toString(), count);
        } catch (MissingDeadlineException e) {
            return e.getMessage();
        }
    }

    public String addEvent(String task) {
        try {
            Task createdTask = new Event(task);
            taskStorage[count] = createdTask;
            count++;
            return String.format("Got it. I've added this task:\n   %s\nNow you have %s tasks in the list.", createdTask.toString(), count);
        } catch (MissingEventException e) {
            return e.getMessage();
        }
    }


    public String showList() {
        String output = "Here are the tasks in your list:\n";
        for (int i = 0; i < count; i++) {
           output += String.format("%s.%s\n", i + 1, taskStorage[i]);
        }
        return output;
    }

    public String markTask(int task) { 
        int correctedTaskIndex = task - 1;
        if (correctedTaskIndex >= count || task <= 0) {
            return "Task does not exist!";
        }
        taskStorage[correctedTaskIndex].markTask();
        return String.format("Nice! I've marked this task as done!:\n   %s", taskStorage[correctedTaskIndex]);
    }

    public String unmarkTask(int task) {
        int correctedTaskIndex = task - 1;
        if (correctedTaskIndex >= count || task <= 0) {
                    return "Task does not exist!";
                }
        taskStorage[correctedTaskIndex].unmarkTask();
        return String.format("Ok! I've marked this task as not done yet!:\n   %s", taskStorage[correctedTaskIndex]);
    }
}
 
