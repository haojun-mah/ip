package audrey.task;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test suite for all task-related classes. Includes tests for Task, Todo, Deadline, Event, and List
 * classes.
 */
@Suite
@SuiteDisplayName("Task Package Test Suite")
@SelectClasses({
    TaskTest.class,
    TodoTest.class,
    DeadlineTest.class,
    EventTest.class,
    ListTest.class
})
public class TaskTestSuite {
    // This class remains empty; it is used only as a holder for the above annotations
}
