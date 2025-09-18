package audrey.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import audrey.exception.MissingDeadlineException;

/** Deadline task containing deadline. */
public class Deadline extends Task {
    private static final String BY_DELIMITER = "/by";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int EXPECTED_PARTS_COUNT = 2;

    private final LocalDate deadline;

    /**
     * Constructor for Deadline task.
     *
     * @param details Details containing task description and deadline
     * @throws MissingDeadlineException If deadline details are missing or invalid
     */
    public Deadline(String details) throws MissingDeadlineException {
        super(processDetail(details));
        deadline = processDeadline(details);

        // Assert: Deadline should be properly initialized
        assert deadline != null : "Deadline should be properly initialized";
    }

    /**
     * Process task detail to sieve out task description.
     *
     * @param detail Task detail
     * @return Task description
     * @throws MissingDeadlineException Error if deadline is missing
     */
    private static String processDetail(String detail) throws MissingDeadlineException {
        // Assert: Detail parameter should not be null
        assert detail != null : "Deadline detail cannot be null";

        String[] processed = detail.split(BY_DELIMITER);

        // Assert: Split should produce an array
        assert processed != null : "Split result should not be null";

        if (!isValidSplitResult(processed) || processed[0].trim().isEmpty()) {
            throw new MissingDeadlineException();
        }

        String result = processed[0].trim();

        // Assert: Processed result should not be empty
        assert !result.isEmpty() : "Processed deadline description should not be empty";

        return result;
    }

    /**
     * Checks if the split result has the expected number of parts.
     *
     * @param splitResult Array from string split operation
     * @return true if split result is valid
     */
    private static boolean isValidSplitResult(String[] splitResult) {
        return splitResult.length == EXPECTED_PARTS_COUNT;
    }

    /**
     * Process task detail to sieve out task deadline.
     *
     * @param detail Task detail
     * @return Task deadline
     * @throws MissingDeadlineException Error if deadline is missing
     */
    private static LocalDate processDeadline(String detail) throws MissingDeadlineException {
        String[] processed = detail.split(BY_DELIMITER);

        if (!isValidSplitResult(processed) || processed[1].trim().isEmpty()) {
            throw new MissingDeadlineException();
        }

        return parseDate(processed[1].trim());
    }

    /**
     * Parses a date string with comprehensive validation.
     *
     * @param dateString Date string to parse
     * @return Parsed LocalDate
     * @throws MissingDeadlineException If date parsing fails or date is invalid
     */
    private static LocalDate parseDate(String dateString) throws MissingDeadlineException {
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new MissingDeadlineException("Date cannot be empty");
        }

        String trimmedDate = dateString.trim();

        // Check basic format before parsing (YYYY-MM-DD)
        if (!trimmedDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new MissingDeadlineException(
                    "Date must be in YYYY-MM-DD format, got: " + trimmedDate);
        }

        try {
            LocalDate parsedDate = LocalDate.parse(trimmedDate);

            // Validate date is not too far in the past
            LocalDate earliestValidDate = LocalDate.now().minusYears(10);
            if (parsedDate.isBefore(earliestValidDate)) {
                throw new MissingDeadlineException("Date too far in the past: " + trimmedDate);
            }

            // Validate date is not too far in the future
            LocalDate latestValidDate = LocalDate.now().plusYears(100);
            if (parsedDate.isAfter(latestValidDate)) {
                throw new MissingDeadlineException("Date too far in the future: " + trimmedDate);
            }

            return parsedDate;

        } catch (java.time.format.DateTimeParseException e) {
            // Check for common invalid dates
            String[] parts = trimmedDate.split("-");
            if (parts.length == 3) {
                try {
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    if (month < 1 || month > 12) {
                        throw new MissingDeadlineException(
                                "Invalid month: " + month + " (must be 1-12)");
                    }

                    if (day < 1 || day > 31) {
                        throw new MissingDeadlineException(
                                "Invalid day: " + day + " (must be 1-31)");
                    }

                    // Special case for common invalid dates
                    if ((month == 2 && day > 29)
                            || ((month == 4 || month == 6 || month == 9 || month == 11)
                                    && day > 30)) {
                        throw new MissingDeadlineException(
                                "Invalid date: "
                                        + trimmedDate
                                        + " (day doesn't exist in that month)");
                    }

                } catch (NumberFormatException nfe) {
                    // Fall through to generic error
                }
            }

            throw new MissingDeadlineException(
                    "Invalid date format: " + trimmedDate + " (use YYYY-MM-DD)");
        } catch (Exception e) {
            throw new MissingDeadlineException(
                    "Error parsing date: " + trimmedDate + " - " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by:%s)", super.toString(), deadline.format(DATE_FORMAT));
    }
}
