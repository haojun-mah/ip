import audrey.command.Parser;
import audrey.task.List;

public class TestParserWhitespace {
    public static void main(String[] args) {
        List taskList = new List();
        Parser parser = new Parser(taskList);
        
        String input = "           list           ";
        String result = parser.runInput(input);
        System.out.println("Input: '" + input + "'");
        System.out.println("Result: '" + result + "'");
        System.out.println("Contains 'To Do List Activated!': " + result.contains("To Do List Activated!"));
    }
}
