package audrey.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Unit tests for Todo class */
public class TodoTest {

    @Test
    @DisplayName("Todo toString should show correct format")
    public void todo_toString_correctFormat() {
        Todo todo = new Todo("buy groceries");
        String expected = "[T][ ] buy groceries";
        assertEquals(expected, todo.toString());
    }

    @Test
    @DisplayName("Todo toString should show correct format when marked")
    public void todo_toString_markedTodo() {
        Todo todo = new Todo("buy groceries");
        todo.markTask();
        String expected = "[T][X] buy groceries";
        assertEquals(expected, todo.toString());
    }

    @Test
    @DisplayName("Todo should inherit Task functionality")
    public void todo_inheritsTaskFunctionality() {
        Todo todo = new Todo("test todo");

        // Test marking/unmarking
        assertFalse(todo.isCompleted());
        todo.markTask();
        assertTrue(todo.isCompleted());
        todo.unmarkTask();
        assertFalse(todo.isCompleted());

        // Test snoozing
        assertFalse(todo.isSnoozed());
        todo.snoozeForever();
        assertTrue(todo.isSnoozed());
        assertTrue(todo.isSnoozedForever());
    }

    @Test
    @DisplayName("Todo with special characters should work correctly")
    public void todo_specialCharacters_handledCorrectly() {
        Todo todo = new Todo("买菜 & go to café");
        String expected = "[T][ ] 买菜 & go to café";
        assertEquals(expected, todo.toString());
    }

    @Test
    @DisplayName("Todo with empty spaces should work correctly")
    public void todo_withSpaces_handledCorrectly() {
        Todo todo = new Todo("  spaced  task  ");
        String expected = "[T][ ]   spaced  task  ";
        assertEquals(expected, todo.toString());
    }
}
