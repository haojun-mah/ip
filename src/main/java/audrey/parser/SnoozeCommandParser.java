package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser that handles {@code snooze} commands with optional dates. */
public class SnoozeCommandParser extends BaseCommandParser {

    /**
     * Builds a parser that snoozes tasks indefinitely or until a supplied date.
     *
     * @param toDoList backing task list to update
     * @param scanner  scanner providing raw user input
     */
    public SnoozeCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    /**
     * Executes a snooze command. When no arguments are supplied it lists snoozable
     * tasks; otherwise it snoozes the specified task forever or until a date.
     *
     * @param processedInput tokenised user input containing the task number and
     *                       optional date
     * @return user-facing confirmation or error message
     */
    @Override
    public String execute(String[] processedInput) {
        // Special case: just "snooze" shows snooze list
        if (processedInput.length < MIN_ARGS_LENGTH
                || processedInput[ARGS_INDEX].trim().isEmpty()) {
            return handleSnoozeList();
        }

        String fullArgs = processedInput[ARGS_INDEX].trim();
        String[] parts = fullArgs.split("\\s+", 2);

        String numberStr = parts[0];

        // Validate task number
        String taskValidationError = validateTaskNumber(numberStr);
        if (taskValidationError != null) {
            return taskValidationError;
        }

        try {
            int taskNumber = Integer.parseInt(numberStr);

            // Check if date is provided
            if (parts.length > 1) {
                String dateStr = parts[1].trim();
                return handleSnoozeUntilDate(taskNumber, dateStr);
            } else {
                return handleSnoozeForever(taskNumber);
            }

        } catch (Exception e) {
            String errorMsg = "Error snoozing task: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }

    /**
     * Shows the list of tasks that can be snoozed.
     *
     * @return formatted snoozable task list or an error message
     */
    private String handleSnoozeList() {
        try {
            String snoozeList = toDoList.showSnoozableTasks();
            print(snoozeList);
            return snoozeList;
        } catch (Exception e) {
            String errorMsg = "Error showing snooze list: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }

    /**
     * Snoozes a task without a wake-up date.
     *
     * @param taskNumber task index from the user-facing list
     * @return result message from the task list
     */
    private String handleSnoozeForever(int taskNumber) {
        try {
            String snoozeResult = toDoList.snoozeTaskForever(taskNumber);
            print(snoozeResult);
            return snoozeResult;
        } catch (Exception e) {
            String errorMsg = "Error snoozing task forever: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }

    /**
     * Snoozes a task until the supplied date.
     *
     * @param taskNumber task index from the user-facing list
     * @param dateStr    date string in {@code YYYY-MM-DD}
     * @return result message from the task list or validation feedback
     */
    private String handleSnoozeUntilDate(int taskNumber, String dateStr) {
        // Validate date format
        if (!dateStr.matches(DATE_PATTERN)) {
            String errorMsg = "Invalid Format for date";
            print(errorMsg);
            return errorMsg;
        }

        try {
            String snoozeResult = toDoList.snoozeTaskUntil(taskNumber, dateStr);
            print(snoozeResult);
            return snoozeResult;
        } catch (Exception e) {
            String errorMsg = "Error snoozing task until date: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
