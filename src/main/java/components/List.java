package components;

public class List {
    private Task[] taskStorage;
    private int count;

    public List() {
        taskStorage = new Task[100]; 
        count = 0;
    }

    public String addToList(String task) {
        Task createdTask = new Task(task);
        taskStorage[count] = createdTask;
        count++;
        return createdTask.toString();
    }

    public String showList() {
        String output = "Here are the tasks in your list:\n";
        for (int i = 0; i < count; i++) {
           output += String.format("%s.%s\n", i + 1, taskStorage[i]);
        }
        return output;
    }

    public void markTask(int task) {
        taskStorage[task].markTask();
    }

    public void unmarkTask(int task) {
        taskStorage[task].unmarkTask();
    }



}
 
