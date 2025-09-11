package audrey.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import audrey.exception.MissingDeadlineException;

/**
 * Deadline task containing deadline.
 */
public class Deadline extends Task {
    private final LocalDate deadline;

    public Deadline(String details) throws MissingDeadlineException {
        // Assert: Details parameter should not be null
        assert details != null : "Deadline details cannot be null";
        
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
        
        String[] processed = detail.split("/by");
        
        // Assert: Split should produce an array
        assert processed != null : "Split result should not be null";
        
        if (processed.length != 2 || processed[0].trim().isEmpty()) {
            throw new MissingDeadlineException();
        }
        
        String result = processed[0].trim();
        
        // Assert: Processed result should not be empty
        assert !result.isEmpty() : "Processed deadline description should not be empty";
        
        return result;
    }

    /**
     * Process task detail to sieve out task deadline.
     *
     * @param detail Task detail
     * @return Task deadline
     * @throws MissingDeadlineException Error if deadline is missing
     */
    private static LocalDate processDeadline(String detail) throws MissingDeadlineException {
        String[] processed = detail.split("/by");
        if (processed.length != 2 || processed[1].trim().isEmpty()) {
            throw new MissingDeadlineException();
        }
        return LocalDate.parse(processed[1].trim());
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by:%s)", super.toString(),
                                        deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
