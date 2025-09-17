package audrey.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import audrey.exception.MissingDeadlineException;

/**
 * Unit test for deadline class
 */
public class DeadlineTest {
    @Test
    @DisplayName("Ensure dateline string format is within expectation")
    public void deadline_toString() {
        Deadline deadline = assertDoesNotThrow(() -> new Deadline("activity /by 1234-12-12"));
        String expected = "[D][ ] activity (by:1234-12-12)";
        String actual = deadline.toString();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ensure that error is thrown if no date")
    public void deadline_noDate_throwMissingDeadlineException() {
        assertThrows(MissingDeadlineException.class, () -> new Deadline("123 /by"));
    }

    @Test
    @DisplayName("Ensure that error is thrown if wrong dateformat is parsed")
    public void deadline_invalidDateFormat_throwMissingDeadlineException() {
        assertThrows(DateTimeParseException.class, () -> new Deadline("123 /by 123-123"));
    }
}
