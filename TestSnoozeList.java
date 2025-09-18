import audrey.task.List;
import audrey.task.Todo;

public class TestSnoozeList {
    public static void main(String[] args) {
        List taskList = new List();
        
        // Add some tasks
        Todo task1 = new Todo("normal task 1");
        Todo task2 = new Todo("normal task 2");
        taskList.add(task1);
        taskList.add(task2);
        
        // Snooze one task
        task2.snooze();
        
        // Test showSnoozableTasks
        String result = taskList.showSnoozableTasks();
        System.out.println("SNOOZE LIST OUTPUT:");
        System.out.println("'" + result + "'");
        System.out.println("---");
        
        // Also test with empty list for completeness
        List emptyList = new List();
        String emptyResult = emptyList.showSnoozableTasks();
        System.out.println("EMPTY SNOOZE LIST OUTPUT:");
        System.out.println("'" + emptyResult + "'");
    }
}
