package components;

class Event extends Task {
    private final String from;
    private final String to;

    public Event(String details) throws MissingEventException{
        super(processDetail(details));
        from = processFrom(details);
        to = processTo(details);
    }

    private static String processDetail(String detail) throws MissingEventException {
        String[] processed = detail.split("/from");
        if (processed.length != 2 || processed[0].trim().isEmpty()) { 
            throw new MissingEventException();
        }
        return processed[0].trim();
    }

    private static String processFrom(String detail) throws MissingEventException {
        String[] processed = detail.split("/from");
        processed = detail.split("/to");
        if (processed.length != 2 || processed[1].trim().isEmpty()) { 
            throw new MissingEventException();
        }
        return processed[1].trim();
    }

    private static String processTo(String detail) throws MissingEventException {
        String[] processed = detail.split("/to");
        if (processed.length != 2 || processed[1].trim().isEmpty()) { 
            throw new MissingEventException();
        }
        return processed[1].trim();
    }

    @Override 
    public String toString() {
        return String.format("[E]%s (from:%s to:%s)", super.toString(), from, to);
    }
}
