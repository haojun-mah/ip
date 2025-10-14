package audrey.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Unit tests for custom exception classes */
public class ExceptionTest {

    @Test
    @DisplayName("MissingDeadlineException should work with default message")
    public void missingDeadlineException_defaultMessage() {
        MissingDeadlineException exception = new MissingDeadlineException();
        assertNotNull(exception.getMessage());
        assertEquals("Missing Deadline Details!", exception.getMessage());
    }

    @Test
    @DisplayName("MissingDeadlineException should work with custom message")
    public void missingDeadlineException_customMessage() {
        String customMessage = "Custom deadline error message";
        MissingDeadlineException exception = new MissingDeadlineException(customMessage);
        assertEquals(customMessage, exception.getMessage());
    }

    @Test
    @DisplayName("MissingEventException should work with default message")
    public void missingEventException_defaultMessage() {
        MissingEventException exception = new MissingEventException();
        assertNotNull(exception.getMessage());
        assertEquals("Missing Event Details!", exception.getMessage());
    }

    @Test
    @DisplayName("MissingEventException should work with expected message")
    public void missingEventException_expectedMessage() {
        MissingEventException exception = new MissingEventException();
        assertEquals("Missing Event Details!", exception.getMessage());
    }

    @Test
    @DisplayName("WrongFromToOrientationException should work with default message")
    public void wrongFromToOrientationException_defaultMessage() {
        WrongFromToOrientationException exception = new WrongFromToOrientationException();
        assertNotNull(exception.getMessage());
        assertEquals("To and From are in the wrong orientation", exception.getMessage());
    }

    @Test
    @DisplayName("WrongFromToOrientationException should work with expected message")
    public void wrongFromToOrientationException_expectedMessage() {
        WrongFromToOrientationException exception = new WrongFromToOrientationException();
        assertEquals("To and From are in the wrong orientation", exception.getMessage());
    }

    @Test
    @DisplayName("Exceptions should be throwable and catchable")
    public void exceptions_throwableAndCatchable() {
        // Test MissingDeadlineException with custom message
        try {
            throw new MissingDeadlineException("Test deadline exception");
        } catch (MissingDeadlineException e) {
            assertEquals("Test deadline exception", e.getMessage());
        }

        // Test MissingEventException with default message
        try {
            throw new MissingEventException();
        } catch (MissingEventException e) {
            assertEquals("Missing Event Details!", e.getMessage());
        }

        // Test WrongFromToOrientationException with default message
        try {
            throw new WrongFromToOrientationException();
        } catch (WrongFromToOrientationException e) {
            assertEquals("To and From are in the wrong orientation", e.getMessage());
        }
    }

    @Test
    @DisplayName("Exceptions should inherit from Exception properly")
    public void exceptions_inheritFromException() {
        MissingDeadlineException deadlineException = new MissingDeadlineException();
        MissingEventException eventException = new MissingEventException();
        WrongFromToOrientationException orientationException =
                new WrongFromToOrientationException();

        assertTrue(deadlineException instanceof Exception);
        assertTrue(eventException instanceof Exception);
        assertTrue(orientationException instanceof Exception);
    }

    @Test
    @DisplayName("MissingDeadlineException should handle null messages gracefully")
    public void missingDeadlineException_nullMessages_handledGracefully() {
        MissingDeadlineException deadlineException = new MissingDeadlineException(null);

        // Should not crash when accessing message (might be null, but shouldn't throw)
        deadlineException.getMessage(); // This should not throw an exception

        // Message access should complete without exception
        assertTrue(true); // Test passed if we reach here without exception
    }

    @Test
    @DisplayName("MissingDeadlineException should handle empty messages")
    public void missingDeadlineException_emptyMessages_handled() {
        MissingDeadlineException deadlineException = new MissingDeadlineException("");
        assertEquals("", deadlineException.getMessage());
    }

    @Test
    @DisplayName("MissingDeadlineException should handle special characters in messages")
    public void missingDeadlineException_specialCharacters_handled() {
        String specialMessage = "Error with special chars: 中文, café, émojis 😀";

        MissingDeadlineException deadlineException = new MissingDeadlineException(specialMessage);
        assertEquals(specialMessage, deadlineException.getMessage());
    }
}
