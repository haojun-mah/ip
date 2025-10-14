package audrey.command;

import java.util.Scanner;

import audrey.task.List;

/** Parser for snooze commands. */
public class SnoozeCommandParser extends BaseCommandParser {

    public SnoozeCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

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

    /** Handles showing the snooze list. */
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

    /** Handles snoozing a task forever. */
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

    /** Handles snoozing a task until a specific date. */
    private String handleSnoozeUntilDate(int taskNumber, String dateStr) {
        // Validate date format
        if (!dateStr.matches(DATE_PATTERN)) {
            String errorMsg = "Invalid date format: '" + dateStr + "'. Use YYYY-MM-DD format.";
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
