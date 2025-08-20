package components;

import java.util.Arrays;

public class List {
    private final String[] storage;
    private final boolean[] taskCompletedStatus;
    private int count;

    public List() {
        storage = new String[100];
        taskCompletedStatus = new boolean[100];
        Arrays.fill(taskCompletedStatus, false);
        count = 0;
    }

    public String addToList(String task) {
        storage[count] = task;
        count++;
        return String.format("added: %s", task);
    }

    public String showList() {
        String output = "Here are the tasks in your list:\n";
        for (int i = 0; i < count; i++) {
           output += String.format("%s.[%s] %s\n", i + 1, taskCompletedStatus[i] ? 'X' : ' ', storage[i]);
        }
        return output;
    }
   

}
