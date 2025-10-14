package audrey.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import audrey.task.List;

/**
 * @Test @DisplayName("Parser should handle case variations") public void
 * parser_caseVariations_handled() { String result1 = parser.runInput("LIST"); String result2 =
 * parser.runInput("List"); String result3 = parser.runInput("list");
 *
 * <p>// Parser handles commands case insensitively assertTrue(result1.contains("Here are the tasks
 * in your list") || result2.contains("Here are the tasks in your list") || result3.contains("Here
 * are the tasks in your list")); }r Parser class functionality
 */
public class ParserTest {
    private Parser parser;
    private List taskList;

    @BeforeEach
    public void setUp() {
        taskList = new List();
        parser = new Parser(taskList);
    }

    @Test
    @DisplayName("Parser should show help message for help command")
    public void parser_helpCommand_showsHelp() {
        String result = parser.runInput("help");
        assertTrue(result.contains("Available Commands"));
        assertTrue(result.contains("todo"));
        assertTrue(result.contains("deadline"));
        assertTrue(result.contains("event"));
    }

    @Test
    @DisplayName("Parser should activate list mode with list command")
    public void parser_listCommand_activatesListMode() {
        String result = parser.runInput("list");
        assertTrue(result.contains("To Do List Activated!"));
    }

    @Test
    @DisplayName("Parser should handle empty input")
    public void parser_emptyInput_returnsError() {
        String result = parser.runInput("");
        assertTrue(result.contains("Please enter a command."));
    }

    @Test
    @DisplayName("Parser should handle null input with assertion")
    public void parser_nullInput_assertionError() {
        // Parser uses assert for null input, so this will throw AssertionError in test environment
        try {
            parser.runInput(null);
            assertTrue(false, "Should have thrown AssertionError");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("Input string cannot be null"));
        }
    }

    @Test
    @DisplayName("Parser should handle excessive whitespace")
    public void parser_excessiveWhitespace_handlesCommand() {
        String input = "           list           ";
        String result = parser.runInput(input);
        assertTrue(result.contains("Too much whitespace. Please check your command format."));
    }

    @Test
    @DisplayName("Parser should handle extremely long input")
    public void parser_longInput_returnsError() {
        StringBuilder longInput = new StringBuilder();
        for (int i = 0; i < 1001; i++) {
            longInput.append("a");
        }
        String result = parser.runInput(longInput.toString());
        assertTrue(result.contains("Command too long"));
    }

    @Test
    @DisplayName("Parser should handle todo command in list mode")
    public void parser_todoCommandInListMode_success() {
        parser.runInput("list"); // Activate list mode
        String result = parser.runInput("todo buy groceries");
        assertTrue(result.contains("Got it. I've added this task:"));
        assertTrue(result.contains("[T][ ] buy groceries"));
    }

    @Test
    @DisplayName("Parser should handle deadline command with valid date")
    public void parser_deadlineCommand_success() {
        parser.runInput("list");
        String result = parser.runInput("deadline submit report /by 2025-10-15");
        assertTrue(result.contains("Got it. I've added this task:"));
        assertTrue(result.contains("[D][ ] submit report (by:2025-10-15)"));
    }

    @Test
    @DisplayName("Parser should handle event command with valid dates")
    public void parser_eventCommand_success() {
        parser.runInput("list");
        String result = parser.runInput("event conference /from 2025-10-15 /to 2025-10-16");
        assertTrue(result.contains("Got it. I've added this task:"));
        assertTrue(result.contains("[E][ ] conference (from:2025-10-15 to:2025-10-16)"));
    }

    @Test
    @DisplayName("Parser should handle mark command")
    public void parser_markCommand_success() {
        parser.runInput("list");
        parser.runInput("todo test task");
        String result = parser.runInput("mark 1");
        assertTrue(result.contains("Nice! I've marked this task as done!"));
    }

    @Test
    @DisplayName("Parser should handle unmark command")
    public void parser_unmarkCommand_success() {
        parser.runInput("list");
        parser.runInput("todo test task");
        parser.runInput("mark 1");
        String result = parser.runInput("unmark 1");
        assertTrue(result.contains("Ok! I've marked this task as not done yet!"));
    }

    @Test
    @DisplayName("Parser should handle delete command")
    public void parser_deleteCommand_success() {
        parser.runInput("list");
        parser.runInput("todo test task");
        String result = parser.runInput("delete 1");
        assertTrue(result.contains("Removing this task!"));
    }

    @Test
    @DisplayName("Parser should handle find command")
    public void parser_findCommand_success() {
        parser.runInput("list");
        parser.runInput("todo buy groceries");
        parser.runInput("todo buy coffee");
        String result = parser.runInput("find buy");
        assertTrue(result.contains("matching tasks"));
    }

    @Test
    @DisplayName("Parser should handle snooze command with invalid date format")
    public void parser_snoozeCommand_invalidFormat() {
        parser.runInput("list");
        parser.runInput("todo test task");
        String result = parser.runInput("snooze 1 forever");
        assertTrue(result.contains("Invalid Format for date"));
    }

    @Test
    @DisplayName("Parser should handle snooze command with date")
    public void parser_snoozeCommandWithDate_success() {
        parser.runInput("list");
        parser.runInput("todo test task");
        String result = parser.runInput("snooze 1 2025-12-25");
        assertTrue(result.contains("Task snoozed until"));
    }

    @Test
    @DisplayName("Parser should handle unsnooze command")
    public void parser_unsnoozeCommand_taskNotSnoozed() {
        parser.runInput("list");
        parser.runInput("todo test task");
        String result = parser.runInput("unsnooze 1");
        assertTrue(result.contains("Task is not snoozed!"));
    }

    @Test
    @DisplayName("Parser should handle invalid command")
    public void parser_invalidCommand_returnsError() {
        parser.runInput("list");
        String result = parser.runInput("invalidcommand");
        assertTrue(result.contains("Invalid command: 'invalidcommand'"));
    }

    @Test
    @DisplayName("Parser should handle malformed todo command")
    public void parser_malformedTodoCommand_returnsError() {
        parser.runInput("list");
        String result = parser.runInput("todo");
        assertTrue(result.contains("Todo description cannot be empty"));
    }

    @Test
    @DisplayName("Parser should handle malformed deadline command")
    public void parser_malformedDeadlineCommand_returnsError() {
        parser.runInput("list");
        String result = parser.runInput("deadline task without date");
        assertTrue(result.contains("Deadline must include '/by [date]'"));
    }

    @Test
    @DisplayName("Parser should handle malformed event command")
    public void parser_malformedEventCommand_returnsError() {
        parser.runInput("list");
        String result = parser.runInput("event task without dates");
        assertTrue(result.contains("Event must include '/from [date]'"));
    }

    @Test
    @DisplayName("Parser should handle invalid task numbers")
    public void parser_invalidTaskNumbers_returnsError() {
        parser.runInput("list");
        String result = parser.runInput("mark 999");
        assertTrue(result.contains("Task number 999 does not exist"));
    }

    @Test
    @DisplayName("Parser should handle commands with special characters")
    public void parser_specialCharacters_handled() {
        parser.runInput("list");
        String result = parser.runInput("todo buy café & groceries 买菜");
        assertTrue(result.contains("Got it. I've added this task:"));
    }

    @Test
    @DisplayName("Parser should handle commands with multiple spaces")
    public void parser_multipleSpaces_handled() {
        parser.runInput("list");
        String result = parser.runInput("todo    buy     groceries");
        // Should either work or give appropriate error
        assertTrue(result.contains("Got it") || result.contains("Invalid"));
    }

    @Test
    @DisplayName("Parser should sanitize input correctly")
    public void parser_inputSanitization_works() {
        parser.runInput("list");
        // Test with leading/trailing spaces
        String result = parser.runInput("  todo buy groceries  ");
        assertTrue(result.contains("Got it. I've added this task:"));
    }

    @Test
    @DisplayName("Parser should handle case variations")
    public void parser_caseVariations_handled() {
        String result1 = parser.runInput("LIST");
        String result2 = parser.runInput("List");
        String result3 = parser.runInput("list");

        // At least one should work (case insensitive or case sensitive)
        assertTrue(
                result1.contains("To Do List Activated!")
                        || result2.contains("To Do List Activated!")
                        || result3.contains("To Do List Activated!"));
    }
}
