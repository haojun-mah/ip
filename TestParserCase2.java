import audrey.command.Parser;
import audrey.task.List;

public class TestParserCase2 {
    public static void main(String[] args) {
        List taskList = new List();
        Parser parser = new Parser(taskList);
        
        String result1 = parser.runInput("LIST");
        System.out.println("LIST: '" + result1 + "'");
        
        String result2 = parser.runInput("List");
        System.out.println("List: '" + result2 + "'");
        
        String result3 = parser.runInput("list");
        System.out.println("list: '" + result3 + "'");
    }
}
