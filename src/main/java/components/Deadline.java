package components;
import java.util.regex.*;;

class Deadline extends Task {
    private final String deadline;
    private static final Pattern pattern = Pattern.compile("/\\s*(\\S+)");

    public Deadline(String details) throws MissingDeadlineException{
        super(pattern.matcher(details).group(0));
        if (pattern.matcher(details).group().length() != 1) {
            throw new MissingDeadlineException();
        } else {
            deadline = pattern.matcher(details).group(1);
        }
    }

    @Override 
    public String toString() {
        return String.format("[T]%s (by:%s)", super.toString(), deadline);
    }
}

