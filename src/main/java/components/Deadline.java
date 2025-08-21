package components;

class Deadline extends Task {
    private final String deadline;

    public Deadline(String details) throws MissingDeadlineException{
        super(processDetail(details));
        deadline = processDeadline(details);
    }

    private static String processDetail(String detail) throws MissingDeadlineException {
        String[] processed = detail.split("/by");
        if (processed.length != 2 || processed[0].trim().isEmpty()) { 
            throw new MissingDeadlineException();
        }
        return processed[0].trim();
    }

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

