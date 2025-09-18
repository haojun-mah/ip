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
     * Parses a date string with proper error handling.
     *
     * @param dateString Date string to parse
     * @return Parsed LocalDate
     * @throws MissingDeadlineException If date parsing fails
     */
    private static LocalDate parseDate(String dateString) throws MissingDeadlineException {
        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new MissingDeadlineException();
        }
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by:%s)", super.toString(), deadline.format(DATE_FORMAT));
    }
}
