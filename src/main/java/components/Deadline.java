package components;

/**
 * Deadline task containing deadline
 */
class Deadline extends Task {
    private final String deadline;

    public Deadline(String details) throws MissingDeadlineException{
        super(processDetail(details));
        deadline = processDeadline(details);
    }

    /**
     * Process task detail to sieve out task description
     * @param detail task detail
     * @return task description
     * @throws MissingDeadlineException error if deadline is missing
     */
    private static String processDetail(String detail) throws MissingDeadlineException {
        String[] processed = detail.split("/by");
        if (processed.length != 2 || processed[0].trim().isEmpty()) { 
            throw new MissingDeadlineException();
        }
        return processed[0].trim();
    }

    /**
     * Process task detail to sieve out task deadline
     * @param detail task detail
     * @return task deadline
     * @throws MissingDeadlineException error if deadline is missing
     */
    private static String processDeadline(String detail) throws MissingDeadlineException {
        String[] processed = detail.split("/by");
        if (processed.length != 2 || processed[1].trim().isEmpty()) { 
            throw new MissingDeadlineException();
        }
        return processed[1].trim();
    }

    @Override 
    public String toString() {
        return String.format("[D]%s (by:%s)", super.toString(), deadline);
    }
}

