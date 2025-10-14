package audrey.parser;

import java.util.Scanner;

import audrey.task.List;

/** Parser for list commands. */
public class ListCommandParser extends BaseCommandParser {

    public ListCommandParser(List toDoList, Scanner scanner) {
        super(toDoList, scanner);
    }

    @Override
    public String execute(String[] processedInput) {
        try {
            String listResult = toDoList.showList();
            print(listResult);
            return listResult;

        } catch (Exception e) {
            String errorMsg = "Error listing tasks: " + e.getMessage();
            print(errorMsg);
            return errorMsg;
        }
    }
}
