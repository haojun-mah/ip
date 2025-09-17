package audrey.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import audrey.exception.MissingEventException;
import audrey.exception.WrongFromToOrientationException;

/**
 * Event task containing from and to.
 */
public class Event extends Task {
    private static final String FROM_DELIMITER = "/from";
    private static final String TO_DELIMITER = "/to";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int EXPECTED_PARTS_COUNT = 2;

    private final LocalDate from;
    private final LocalDate to;

    /**
     * Constructor for Event task.
     *
     * @param details Details containing task description, from date, and to date
     * @throws MissingEventException If event details are missing
     * @throws WrongFromToOrientationException If from date is after to date
     */
    public Event(String details) throws MissingEventException, WrongFromToOrientationException {
        super(processDetail(details));
        from = processFrom(details);
        to = processTo(details);

        // Assert: Both dates should be properly initialized
        assert from != null : "From date should be properly initialized";
        assert to != null : "To date should be properly initialized";

        // Validate that from date is not after to date
        if (from.isAfter(to)) {
            throw new WrongFromToOrientationException();
        }

        // Assert: After validation, from date should not be after to date
        assert !from.isAfter(to) : "From date should not be after to date";
    }

    /**
     * Process task description from task detail.
     *
     * @param detail Task detail
     * @return Task description
     * @throws MissingEventException Error if missing from and to detail
     */
    private static String processDetail(String detail) throws MissingEventException {
        // Assert: Detail parameter should not be null
        assert detail != null : "Event detail cannot be null";

        String[] processed = detail.split(FROM_DELIMITER);

        // Assert: Split should produce an array
        assert processed != null : "Split result should not be null";

        if (!isValidSplitResult(processed) || processed[0].trim().isEmpty()) {
            throw new MissingEventException();
        }

        String result = processed[0].trim();

        // Assert: Processed result should not be empty
        assert !result.isEmpty() : "Processed event description should not be empty";

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
     * Process from info from task detail.
     *
     * @param detail Task detail
     * @return From info
     * @throws MissingEventException Error if missing from and to detail
     */
    private static LocalDate processFrom(String detail) throws MissingEventException {
        String[] fromSplit = detail.split(FROM_DELIMITER);
        if (!isValidSplitResult(fromSplit)) {
            throw new MissingEventException();
        }

        String afterFromPart = fromSplit[1].trim();
        String[] toSplit = afterFromPart.split(TO_DELIMITER);

        if (!isValidSplitResult(toSplit) || toSplit[0].trim().isEmpty()) {
            throw new MissingEventException();
        }

        return parseDate(toSplit[0].trim());
    }

    /**
     * Process to info from task detail.
     *
     * @param detail Task detail
     * @return To info
     * @throws MissingEventException Error if missing from and to detail
     */
    private static LocalDate processTo(String detail) throws MissingEventException {
        String[] processed = detail.split(TO_DELIMITER);

        if (!isValidSplitResult(processed) || processed[1].trim().isEmpty()) {
            throw new MissingEventException();
        }

        return parseDate(processed[1].trim());
    }

    /**
     * Parses a date string with proper error handling.
     *
     * @param dateString Date string to parse
     * @return Parsed LocalDate
     * @throws MissingEventException If date parsing fails
     */
    private static LocalDate parseDate(String dateString) throws MissingEventException {
        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new MissingEventException();
        }
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from:%s to:%s)", super.toString(), from.format(DATE_FORMAT),
                to.format(DATE_FORMAT));
    }
}
