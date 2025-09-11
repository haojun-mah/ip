package audrey.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import audrey.exception.MissingEventException;
import audrey.exception.WrongFromToOrientationException;

/**
 * Event task containing from and to.
 */
public class Event extends Task {
    private final LocalDate from;
    private final LocalDate to;

    public Event(String details) throws MissingEventException, WrongFromToOrientationException {
        // Assert: Details parameter should not be null
        assert details != null : "Event details cannot be null";
        
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
        
        String[] processed = detail.split("/from");
        
        // Assert: Split should produce an array
        assert processed != null : "Split result should not be null";
        
        if (processed.length != 2 || processed[0].trim().isEmpty()) {
            throw new MissingEventException();
        }
        
        String result = processed[0].trim();
        
        // Assert: Processed result should not be empty
        assert !result.isEmpty() : "Processed event description should not be empty";
        
        return result;
    }

    /**
     * Process from info from task detail.
     *
     * @param detail Task detail
     * @return From info
     * @throws MissingEventException Error if missing from and to detail
     */
    private static LocalDate processFrom(String detail) throws MissingEventException {
        String[] fromSplit = detail.split("/from");
        if (fromSplit.length != 2) {
            throw new MissingEventException();
        }

        String afterFrom = fromSplit[1].trim();
        String[] toSplit = afterFrom.split("/to");
        if (toSplit.length != 2 || toSplit[0].trim().isEmpty()) {
            throw new MissingEventException();
        }

        try {
            return LocalDate.parse(toSplit[0].trim());
        } catch (Exception e) {
            throw new MissingEventException();
        }
    }

    /**
     * Process to info from task detail.
     *
     * @param detail Task detail
     * @return To info
     * @throws MissingEventException Error if missing from and to detail
     */
    private static LocalDate processTo(String detail) throws MissingEventException {
        String[] processed = detail.split("/to");
        if (processed.length != 2 || processed[1].trim().isEmpty()) {
            throw new MissingEventException();
        }
        return LocalDate.parse(processed[1].trim());
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from:%s to:%s)", super.toString(),
                                        from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                        to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
