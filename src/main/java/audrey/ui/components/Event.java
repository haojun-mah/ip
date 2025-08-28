package audrey.ui.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Event task containing from and to
 */
class Event extends Task {
    private final LocalDate from;
    private final LocalDate to;

    public Event(String details) throws MissingEventException{
        super(processDetail(details));
        from = processFrom(details);
        to = processTo(details);
    }

    /**
     * Process task description from task detail
     * @param detail task detail
     * @return task description
     * @throws MissingEventException error if missing from and to detail
     */
    private static String processDetail(String detail) throws MissingEventException {
        String[] processed = detail.split("/from");
        if (processed.length != 2 || processed[0].trim().isEmpty()) { 
            throw new MissingEventException();
        }
        return processed[0].trim();
    }

    /**
     * Process from info from task detail
     * @param detail task detail
     * @return from info
     * @throws MissingEventException error if missing from and to detail
     */
    private static LocalDate processFrom(String detail) throws MissingEventException {
        String[] processed = detail.split("/from");
        processed = detail.split("/to");
        if (processed.length != 2 || processed[1].trim().isEmpty()) { 
            throw new MissingEventException();
        }
        return LocalDate.parse(processed[1].trim());
    }

    /**
     * Process to info from task detail
     * @param detail task detail
     * @return to info
     * @throws MissingEventException error if missing from and to detail
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
