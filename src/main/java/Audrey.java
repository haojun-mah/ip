import components.Command;
import components.List;
import components.Parser;
import components.Storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Contains logic for bot workflow
 * 
*/
public class Audrey {
    private static final String AUDREY_DB = "audrey_db.txt";
    
    /**
     * Entry point
     * @param args
     */
    public static void main(String[] args) {
        String logo = "\n" +
                "  #####  ##   ## #####  ##### ####### ##   ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##      ##  ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##       ## ##\n" +
                " ####### ##   ## ##  ## #####  #####     ### \n" +
                " ##   ## ##   ## ##  ## ##  ## ##          ##\n" +
                " ##   ## ##   ## ##  ## ##  ## ##          ##\n" +
                " ##   ##  #####  #####  ##  ## #######     ##\n";
        print("Hello! I'm Audrey\nWhat can I do for you!\n" + logo);

        Storage audreyStorage = new Storage(AUDREY_DB);
        List toDoList = audreyStorage.getToDoList();
        Parser command = new Parser(toDoList);

        try {
            command.runInput();
        } catch (Exception e) {
            print("Error with parser");
        } finally {
            audreyStorage.saveToFile();;
            print("Bye! Hope to see you again!");
        }
    }

    private static void print(String string) {
        String[] splitString = string.split("\n");
        StringBuilder formattedString = new StringBuilder();
        for (int i = 0; i < splitString.length; i++) {
            if (i + 1 == splitString.length) {
                formattedString.append("    ").append(splitString[i]);
            } else {
                formattedString.append("    ").append(splitString[i]).append('\n');
            }
        }
        System.out.println("    ____________________________________________________________________");
        System.out.println(formattedString.toString());
        System.out.println("    ____________________________________________________________________");
    }

} 
