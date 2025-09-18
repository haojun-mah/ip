package audrey.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Unit tests for Task class functionality */
public class TaskTest {
    private Todo testTask;

    @BeforeEach
    public void setUp() {
        testTask = new Todo("test task");
    }

    @Test
    @DisplayName("Task should be unmarked by default")
    public void task_defaultState_isNotCompleted() {
        assertFalse(testTask.isCompleted());
    }

    @Test
    @DisplayName("Task should be marked after calling markTask")
    public void task_markTask_isCompleted() {
        testTask.markTask();
        assertTrue(testTask.isCompleted());
    }

    @Test
    @DisplayName("Task should be unmarked after calling unmarkTask")
    public void task_unmarkTask_isNotCompleted() {
        testTask.markTask();
        testTask.unmarkTask();
        assertFalse(testTask.isCompleted());
    }

    @Test
    @DisplayName("Task should not be snoozed by default")
    public void task_defaultState_isNotSnoozed() {
        assertFalse(testTask.isSnoozed());
    }

    @Test
    @DisplayName("Task should be snoozed until future date")
    public void task_snoozeUntilFutureDate_isSnoozed() {
        LocalDate futureDate = LocalDate.now().plusDays(5);
        testTask.snooze(futureDate);
        assertTrue(testTask.isSnoozed());
        assertEquals(futureDate, testTask.getSnoozeUntil());
    }

    @Test
    @DisplayName("Task snoozed until past date should not be snoozed")
    public void task_snoozeUntilPastDate_isNotSnoozed() {
        LocalDate pastDate = LocalDate.now().minusDays(5);
        testTask.snooze(pastDate);
        assertFalse(testTask.isSnoozed());
    }

    @Test
    @DisplayName("Task should be snoozed forever")
    public void task_snoozeForever_isSnoozedForever() {
        testTask.snoozeForever();
        assertTrue(testTask.isSnoozed());
        assertTrue(testTask.isSnoozedForever());
        assertEquals(LocalDate.MAX, testTask.getSnoozeUntil());
    }

    @Test
    @DisplayName("Task should be unsnoozed after unsnooze")
    public void task_unsnooze_isNotSnoozed() {
        testTask.snoozeForever();
        testTask.unsnooze();
        assertFalse(testTask.isSnoozed());
        assertFalse(testTask.isSnoozedForever());
        assertEquals(null, testTask.getSnoozeUntil());
    }

    @Test
    @DisplayName("Task toString should show correct format when unmarked")
    public void task_toString_unmarkedTask() {
        String expected = "[T][ ] test task";
        assertEquals(expected, testTask.toString());
    }

    @Test
    @DisplayName("Task toString should show correct format when marked")
    public void task_toString_markedTask() {
        testTask.markTask();
        String expected = "[T][X] test task";
        assertEquals(expected, testTask.toString());
    }

    @Test
    @DisplayName("Task toString should show snooze info when snoozed")
    public void task_toString_snoozedTask() {
        LocalDate futureDate = LocalDate.now().plusDays(3);
        testTask.snooze(futureDate);
        String expected = "[T][ ] test task (snoozed until " + futureDate + ")";
        assertEquals(expected, testTask.toString());
    }

    @Test
    @DisplayName("Task toString should show forever snooze info")
    public void task_toString_snoozedForeverTask() {
        testTask.snoozeForever();
        String expected = "[T][ ] test task (snoozed forever)";
        assertEquals(expected, testTask.toString());
    }
}
