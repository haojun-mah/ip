import audrey.command.Parser;

public class TestParserCase {
    public static void main(String[] args) {
        Parser parser = new Parser();
        
        String result1 = parser.runInput("LIST");
        System.out.println("LIST: '" + result1 + "'");
        
        String result2 = parser.runInput("List");
        System.out.println("List: '" + result2 + "'");
        
        String result3 = parser.runInput("list");
        System.out.println("list: '" + result3 + "'");
    }
}
