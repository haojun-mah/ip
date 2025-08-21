package components;
import java.util.regex.*;;

class Deadline extends Task {
    private final String deadline;
    private static final Pattern pattern = Pattern.compile("/\\s*(\\S+)");

    public Deadline(String details) {
        super(pattern.matcher(details).group(0));
        deadline = pattern.matcher(details).group(1);
    }

    @Override 
    public String toString() {
        return String.format("[T]%s (by:%s)", super.toString(), deadline);
    }
}

