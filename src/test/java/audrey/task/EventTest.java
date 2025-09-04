package audrey.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import audrey.exception.MissingEventException;

/**
 * Unit test for Event class
 */
public class EventTest {

    @Test
    @DisplayName("Ensure dateline string format is within expectation")
    public void deadline_toString() {
        Event event = assertDoesNotThrow(() -> new Event("activity /from 1234-12-12 /to 1234-12-23"));
        String expected = "[E][ ] activity (from:1234-12-12 to:1234-12-23)";
        String actual = event.toString();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ensure that error is thrown if no missing either date for from and to")
    public void event_noDate_throwMissingEventException() {
        assertThrows(MissingEventException.class, () -> new Event("123 /from123 /to"));
    }

    @Test
    @DisplayName("Ensure that error is thrown if wrong dateformat is parsed")
    public void event_invalidDateFormat_throwEventDeadlineException() {
        assertThrows(MissingEventException.class, () -> new Event("123 /from 123-123 /to 123-123"));
    }
}
