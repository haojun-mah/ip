import audrey.task.List;

public class TestEmptyList {
    public static void main(String[] args) {
        List emptyList = new List();
        String result = emptyList.showList();
        System.out.println("showList() result:");
        System.out.println("'" + result + "'");
        System.out.println("isEmpty(): " + result.isEmpty());
        System.out.println("contains 'No tasks found': " + result.contains("No tasks found"));
    }
}
