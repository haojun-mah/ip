package components;

abstract class Task {
    private final String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        completed = false;
    }

    public void markTask() {
        completed = true;
    }

    public void unmarkTask() {
        completed = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", completed ? 'X' : ' ', description);
    }
}


