package audrey.task;

/**
 * Task class for Todo, Deadline and Event to inherit from.
 */
public abstract class Task {
    private final String description;
    private boolean completed;

    public Task(String description) {
        // Assert: Task description should not be null or empty
        assert description != null : "Task description cannot be null";
        assert !description.trim().isEmpty() : "Task description cannot be empty";
        
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
