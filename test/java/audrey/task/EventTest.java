package audrey.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import audrey.exception.MissingEventException;
import audrey.exception.WrongFromToOrientationException;

/** Unit test for Event class */
public class EventTest {

    @Test
    @DisplayName("Ensure event string format is within expectation")
    public void event_toString_correctFormat() {
        Event event =
                assertDoesNotThrow(() -> new Event("activity /from 2025-12-12 /to 2025-12-23"));
        String expected = "[E][ ] activity (from:2025-12-12 to:2025-12-23)";
        String actual = event.toString();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ensure that error is thrown if missing date for from or to")
    public void event_noDate_throwMissingEventException() {
        assertThrows(MissingEventException.class, () -> new Event("123 /from /to"));
        assertThrows(MissingEventException.class, () -> new Event("123 /from 2025-01-01 /to"));
        assertThrows(MissingEventException.class, () -> new Event("123 /from /to 2025-01-01"));
    }

    @Test
    @DisplayName("Ensure that error is thrown if wrong dateformat is parsed")
    public void event_invalidDateFormat_throwsException() {
        assertThrows(MissingEventException.class, () -> new Event("123 /from 123-123 /to 123-123"));
        assertThrows(
                MissingEventException.class,
                () -> new Event("123 /from 2025-01-01 /to not-a-date"));
        assertThrows(
                MissingEventException.class, () -> new Event("123 /from bad-date /to 2025-01-01"));
    }

    @Test
    @DisplayName("Event should throw error when from date is after to date")
    public void event_fromAfterTo_throwsWrongOrientationException() {
        assertThrows(
                WrongFromToOrientationException.class,
                () -> new Event("backwards /from 2025-12-31 /to 2025-01-01"));
    }

    @Test
    @DisplayName("Event with same from and to date should work")
    public void event_sameFromAndToDate_works() {
        Event event =
                assertDoesNotThrow(() -> new Event("same day /from 2025-12-12 /to 2025-12-12"));
        assertTrue(event.toString().contains("from:2025-12-12 to:2025-12-12"));
    }

    @Test
    @DisplayName("Event with marked task should show X")
    public void event_markedTask_showsX() {
        Event event =
                assertDoesNotThrow(() -> new Event("meeting /from 2025-10-15 /to 2025-10-16"));
        event.markTask();
        String expected = "[E][X] meeting (from:2025-10-15 to:2025-10-16)";
        assertEquals(expected, event.toString());
    }

    @Test
    @DisplayName("Event should handle descriptions with keywords")
    public void event_descriptionWithKeywords_handled() {
        Event event =
                assertDoesNotThrow(
                        () -> new Event("go from home to work /from 2025-10-20 /to 2025-10-21"));
        assertTrue(event.toString().contains("go from home to work"));
        assertTrue(event.toString().contains("from:2025-10-20"));
        assertTrue(event.toString().contains("to:2025-10-21"));
    }

    @Test
    @DisplayName("Event should allow dates in past or future")
    public void event_extremeDates_allowed() {
        // Test that extreme dates are allowed (no exceptions thrown)
        assertDoesNotThrow(
                () -> {
                    Event oldEvent = new Event("old /from 1900-01-01 /to 1900-01-02");
                    assertTrue(oldEvent.toString().contains("1900-01-01"));
                    assertTrue(oldEvent.toString().contains("1900-01-02"));
                });

        assertDoesNotThrow(
                () -> {
                    Event futureEvent = new Event("future /from 3000-01-01 /to 3000-01-02");
                    assertTrue(futureEvent.toString().contains("3000-01-01"));
                    assertTrue(futureEvent.toString().contains("3000-01-02"));
                });
    }

    @Test
    @DisplayName("Event should handle leap year dates")
    public void event_leapYear_handled() {
        assertDoesNotThrow(() -> new Event("leap year /from 2024-02-28 /to 2024-02-29"));
    }

    @Test
    @DisplayName("Event should inherit task functionality")
    public void event_inheritsTaskFunctionality() {
        Event event = assertDoesNotThrow(() -> new Event("test /from 2025-10-15 /to 2025-10-16"));

        // Test snoozing
        event.snoozeForever();
        assertTrue(event.isSnoozed());
        assertTrue(event.toString().contains("snoozed forever"));
    }

    @Test
    @DisplayName("Event should reject invalid date combinations")
    public void event_invalidDateCombinations_throwsException() {
        // Invalid dates
        assertThrows(
                MissingEventException.class,
                () -> new Event("bad /from 2025-02-30 /to 2025-03-01"));
        assertThrows(
                MissingEventException.class,
                () -> new Event("bad /from 2025-01-01 /to 2025-13-01"));
    }
}
