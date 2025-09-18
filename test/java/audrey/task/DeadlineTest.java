package audrey.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import audrey.exception.MissingDeadlineException;

/** Unit test for deadline class */
public class DeadlineTest {
    @Test
    @DisplayName("Ensure dateline string format is within expectation")
    public void deadline_toString() {
        Deadline deadline = assertDoesNotThrow(() -> new Deadline("activity /by 2025-12-12"));
        String expected = "[D][ ] activity (by:2025-12-12)";
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
        assertThrows(MissingDeadlineException.class, () -> new Deadline("123 /by 123-123"));
    }

    @Test
    @DisplayName("Deadline with marked task should show X")
    public void deadline_markedTask_showsX() {
        Deadline deadline = assertDoesNotThrow(() -> new Deadline("study /by 2025-10-15"));
        deadline.markTask();
        String expected = "[D][X] study (by:2025-10-15)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    @DisplayName("Deadline should reject dates too far in past")
    public void deadline_pastDate_throwsException() {
        assertThrows(MissingDeadlineException.class, () -> new Deadline("old task /by 1900-01-01"));
    }

    @Test
    @DisplayName("Deadline should reject invalid date like Feb 30")
    public void deadline_invalidDate_throwsException() {
        assertThrows(
                MissingDeadlineException.class, () -> new Deadline("impossible /by 2025-02-30"));
    }

    @Test
    @DisplayName("Deadline should reject dates too far in future")
    public void deadline_futureDateTooFar_throwsException() {
        assertThrows(
                MissingDeadlineException.class, () -> new Deadline("far future /by 3000-01-01"));
    }

    @Test
    @DisplayName("Deadline should handle edge case dates correctly")
    public void deadline_edgeCaseDates_handled() {
        // Leap year date
        assertDoesNotThrow(() -> new Deadline("leap year /by 2024-02-29"));

        // Year boundary
        assertDoesNotThrow(() -> new Deadline("new year /by 2026-01-01"));
        assertDoesNotThrow(() -> new Deadline("year end /by 2025-12-31"));
    }

    @Test
    @DisplayName("Deadline should reject malformed date strings")
    public void deadline_malformedDates_throwsException() {
        assertThrows(MissingDeadlineException.class, () -> new Deadline("bad /by not-a-date"));
        assertThrows(MissingDeadlineException.class, () -> new Deadline("bad /by 25-12-2025"));
        assertThrows(MissingDeadlineException.class, () -> new Deadline("bad /by 2025/12/25"));
        assertThrows(MissingDeadlineException.class, () -> new Deadline("bad /by 2025-13-01"));
        assertThrows(MissingDeadlineException.class, () -> new Deadline("bad /by 2025-01-32"));
    }

    @Test
    @DisplayName("Deadline should handle descriptions with /by in content")
    public void deadline_descriptionWithByKeyword_handled() {
        Deadline deadline =
                assertDoesNotThrow(() -> new Deadline("buy groceries by the store /by 2025-10-20"));
        assertTrue(deadline.toString().contains("buy groceries by the store"));
        assertTrue(deadline.toString().contains("(by:2025-10-20)"));
    }

    @Test
    @DisplayName("Deadline should inherit task functionality")
    public void deadline_inheritsTaskFunctionality() {
        Deadline deadline = assertDoesNotThrow(() -> new Deadline("test /by 2025-10-15"));

        // Test snoozing
        deadline.snoozeForever();
        assertTrue(deadline.isSnoozed());
        assertTrue(deadline.toString().contains("snoozed forever"));
    }
}
