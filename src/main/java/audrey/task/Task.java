package audrey.task;

import java.time.LocalDate;

/** Task class for Todo, Deadline and Event to inherit from. */
public abstract class Task {
    private final String description;
    private boolean completed;
    private LocalDate snoozeUntil; // null means not snoozed, LocalDate.MAX means snoozed forever

    /**
     * Constructor for Task.
     *
     * @param description Description of the task
     */
    public Task(String description) {
        // Assert: Task description should not be null or empty
        assert description != null : "Task description cannot be null";
        assert !description.trim().isEmpty() : "Task description cannot be empty";

        this.description = description;
        completed = false;
        snoozeUntil = null; // Not snoozed by default
    }

    public void markTask() {
        completed = true;
    }

    public void unmarkTask() {
        completed = false;
    }

    /**
     * Check if this task is completed.
     *
     * @return true if the task is completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Snooze this task until a specific date.
     *
     * @param until The date to snooze until, or LocalDate.MAX for forever
     */
    public void snooze(LocalDate until) {
        // Assert: Snooze date should not be null
        assert until != null : "Snooze date cannot be null";

        this.snoozeUntil = until;
    }

    /** Snooze this task forever. */
    public void snoozeForever() {
        this.snoozeUntil = LocalDate.MAX;
    }

    /** Unsnooze this task (remove snooze). */
    public void unsnooze() {
        this.snoozeUntil = null;
    }

    /**
     * Check if this task is currently snoozed.
     *
     * @return true if task is snoozed (including if snooze date hasn't passed yet)
     */
    public boolean isSnoozed() {
        if (snoozeUntil == null) {
            return false;
        }
        if (snoozeUntil.equals(LocalDate.MAX)) {
            return true; // Snoozed forever
        }
        return !snoozeUntil.isBefore(LocalDate.now());
    }

    /**
     * Check if this task is snoozed forever.
     *
     * @return true if task is snoozed forever
     */
    public boolean isSnoozedForever() {
        return snoozeUntil != null && snoozeUntil.equals(LocalDate.MAX);
    }

    /**
     * Get the snooze date.
     *
     * @return snooze date, LocalDate.MAX if snoozed forever, null if not snoozed
     */
    public LocalDate getSnoozeUntil() {
        return snoozeUntil;
    }

    @Override
    public String toString() {
        String statusIcon = completed ? "X" : " ";
        String snoozeInfo = "";

        if (isSnoozed()) {
            if (isSnoozedForever()) {
                snoozeInfo = " (snoozed forever)";
            } else {
                snoozeInfo = " (snoozed until " + snoozeUntil + ")";
            }
        }

        return String.format("[%s] %s%s", statusIcon, description, snoozeInfo);
    }
}
