package components;
import java.util.regex.*;

class Event extends Task {
    private final String from;
    private final String to;
    private static final Pattern pattern = Pattern.compile("/\\s*(\\S+)/\\s*(\\S+)");

    public Event(String details) {
        super(pattern.matcher(details).group(0));
        from = pattern.matcher(details).group(1);
        to = pattern.matcher(details).group(2);
    }

    @Override 
    public String toString() {
        return String.format("[E]%s (from:%s to:%s)", super.toString(), from, to);
    }
}
