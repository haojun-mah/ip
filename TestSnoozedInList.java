import audrey.task.List;

public class TestSnoozedInList {
    public static void main(String[] args) {
        List taskList = new List();
        taskList.addToDos("normal task");
        taskList.addToDos("snoozed task");
        taskList.snoozeTaskForever(2);
        
        String result = taskList.showSnoozableTasks();
        System.out.println("Full result:");
        System.out.println("'" + result + "'");
        System.out.println("---");
        System.out.println("Contains 'snoozed task': " + result.contains("snoozed task"));
        
        String[] lines = result.split("\n");
        for (int i = 0; i < lines.length; i++) {
            System.out.println("Line " + i + ": '" + lines[i] + "'");
        }
    }
}
