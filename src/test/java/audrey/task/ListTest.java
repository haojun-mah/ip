package audrey.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Unit tests for List class functionality */
public class ListTest {
    private List taskList;

    @BeforeEach
    public void setUp() {
        taskList = new List();
    }

    @Test
    @DisplayName("New list should be empty")
    public void list_newList_isEmpty() {
        String result = taskList.showList();
        assertTrue(result.contains("No active tasks!"));
    }

    @Test
    @DisplayName("Adding todo should work correctly")
    public void list_addTodo_success() {
        String result = taskList.addToDos("buy groceries");
        assertTrue(result.contains("Got it. I've added this task:"));
        assertTrue(result.contains("[T][ ] buy groceries"));
        assertTrue(result.contains("Now you have 1 tasks in the list."));
    }

    @Test
    @DisplayName("Adding deadline should work correctly")
    public void list_addDeadline_success() {
        String result = taskList.addDeadline("submit report /by 2025-10-15");
        assertTrue(result.contains("Got it. I've added this task:"));
        assertTrue(result.contains("[D][ ] submit report (by:2025-10-15)"));
        assertTrue(result.contains("Now you have 1 tasks in the list."));
    }

    @Test
    @DisplayName("Adding event should work correctly")
    public void list_addEvent_success() {
        String result = taskList.addEvent("conference /from 2025-10-15 /to 2025-10-16");
        assertTrue(result.contains("Got it. I've added this task:"));
        assertTrue(result.contains("[E][ ] conference (from:2025-10-15 to:2025-10-16)"));
        assertTrue(result.contains("Now you have 1 tasks in the list."));
    }

    @Test
    @DisplayName("Marking task should work correctly")
    public void list_markTask_success() {
        taskList.addToDos("test task");
        String result = taskList.markTask(1);
        assertTrue(result.contains("Nice! I've marked this task as done!"));
        assertTrue(result.contains("[T][X] test task"));
    }

    @Test
    @DisplayName("Unmarking task should work correctly")
    public void list_unmarkTask_success() {
        taskList.addToDos("test task");
        taskList.markTask(1);
        String result = taskList.unmarkTask(1);
        assertTrue(result.contains("Ok! I've marked this task as not done yet!"));
        assertTrue(result.contains("[T][ ] test task"));
    }

    @Test
    @DisplayName("Deleting task should work correctly")
    public void list_deleteTask_success() {
        taskList.addToDos("test task");
        String result = taskList.delete(1);
        assertTrue(result.contains("Removing this task!"));
        assertTrue(result.contains("Now you have 0 task in your list!"));
    }

    @Test
    @DisplayName("Getting task list should show all tasks")
    public void list_showList_showsAllTasks() {
        taskList.addToDos("task 1");
        taskList.addToDos("task 2");
        taskList.addDeadline("task 3 /by 2025-10-15");

        String result = taskList.showList();
        assertTrue(result.contains("Here are the tasks in your list:"));
        assertTrue(result.contains("1.[T][ ] task 1"));
        assertTrue(result.contains("2.[T][ ] task 2"));
        assertTrue(result.contains("3.[D][ ] task 3 (by:2025-10-15)"));
    }

    @Test
    @DisplayName("Finding tasks should work correctly")
    public void list_findTasks_success() {
        taskList.addToDos("buy groceries");
        taskList.addToDos("buy coffee");
        taskList.addToDos("read book");

        ArrayList<Task> result = taskList.findTasks("buy");
        assertEquals(2, result.size());
        assertTrue(result.get(0).toString().contains("buy groceries"));
        assertTrue(result.get(1).toString().contains("buy coffee"));
    }

    @Test
    @DisplayName("Finding with no matches should return empty list")
    public void list_findTasks_noMatches() {
        taskList.addToDos("buy groceries");
        ArrayList<Task> result = taskList.findTasks("nonexistent");
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Snoozing task forever should work")
    public void list_snoozeTaskForever_success() {
        taskList.addToDos("test task");
        String result = taskList.snoozeTaskForever(1);
        assertTrue(result.contains("Task snoozed forever:"));
        assertTrue(result.contains("[T][ ] test task (snoozed forever)"));
    }

    @Test
    @DisplayName("Snoozing task until date should work")
    public void list_snoozeTaskUntilDate_success() {
        taskList.addToDos("test task");
        String result = taskList.snoozeTaskUntil(1, "2025-12-25");
        assertTrue(result.contains("Task snoozed until 2025-12-25:"));
    }

    @Test
    @DisplayName("Unsnoozing task should work")
    public void list_unsnoozeTask_success() {
        taskList.addToDos("test task");
        taskList.snoozeTaskForever(1);
        String result = taskList.unsnoozeTask(1);
        assertTrue(result.contains("Task unsnoozed:"));
        assertTrue(result.contains("[T][ ] test task"));
    }

    @Test
    @DisplayName("Invalid task number should return error")
    public void list_invalidTaskNumber_returnsError() {
        String result = taskList.markTask(999);
        assertTrue(result.contains("Task does not exist!"));

        result = taskList.delete(999);
        assertTrue(result.contains("Task does not exist!"));

        result = taskList.unmarkTask(999);
        assertTrue(result.contains("Task does not exist!"));
    }

    @Test
    @DisplayName("Invalid deadline format should return error message")
    public void list_invalidDeadlineFormat_returnsError() {
        String result = taskList.addDeadline("invalid deadline");
        assertTrue(result.contains("Missing Deadline Details!"));

        result = taskList.addDeadline("task /by invalid-date");
        assertTrue(result.contains("Date must be in YYYY-MM-DD format"));
    }

    @Test
    @DisplayName("Invalid event format should return error message")
    public void list_invalidEventFormat_returnsError() {
        String result = taskList.addEvent("invalid event");
        assertTrue(result.contains("Missing Event Details!"));

        result = taskList.addEvent("task /from 2025-01-01");
        assertTrue(result.contains("Missing Event Details!"));

        result = taskList.addEvent("task /from invalid /to 2025-01-01");
        assertTrue(result.contains("Missing Event Details!"));
    }

    @Test
    @DisplayName("Getting snoozable tasks should show active tasks")
    public void list_showSnoozableTasks_showsActiveTasks() {
        taskList.addToDos("normal task");
        taskList.addToDos("snoozed task");
        taskList.snoozeTaskForever(2);

        String result = taskList.showSnoozableTasks();
        assertTrue(result.contains("Here are the tasks you can snooze:"));
        assertTrue(result.contains("1.[T][ ] normal task"));
        assertTrue(
                result.contains(
                        "2.[T][ ] snoozed task [SNOOZED FOREVER]")); // Snoozed tasks do appear in
        // snooze list for unsnoozing
    }

    @Test
    @DisplayName("Getting specific task should work")
    public void list_getTask_success() {
        taskList.addToDos("test task");
        Task task = taskList.getTask(0); // 0-indexed for getTask
        assertTrue(task.toString().contains("test task"));
    }

    @Test
    @DisplayName("Getting active tasks should exclude snoozed tasks")
    public void list_getActiveTasks_excludesSnoozed() {
        taskList.addToDos("active task");
        taskList.addToDos("snoozed task");
        taskList.snoozeTaskForever(2);

        ArrayList<Task> activeTasks = taskList.getActiveTasks();
        assertEquals(1, activeTasks.size());
        assertTrue(activeTasks.get(0).toString().contains("active task"));
    }
}
