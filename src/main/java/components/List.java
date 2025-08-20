package components;

public class List {
    private final String[] storage;
    private int count;

    public List() {
        storage = new String[100];
        count = 0;
    }

    public String addToList(String task) {
        storage[count] = task;
        count++;
        return String.format("added: %s", task);
    }

    public String showList() {
        String output = "";
        for (int i = 0; i < count; i++) {
           output += String.format("%s. %s\n", i + 1, storage[i]);
        }
        return output;
    }

}
