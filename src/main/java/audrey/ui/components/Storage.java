package audrey.ui.components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    private File db;
    private final List toDoList; 

    public Storage(String filePath) {
        this.filePath = filePath;
        this.db = new File(filePath);
        this.toDoList = new List();  
        
        if (db.exists()) {
            loadFromFile();
        } else {
            createNewFile();
        }
    }

    /**
     * Internal function which load db information into List
     */
    private void loadFromFile() {
        try (Scanner fileScanner = new Scanner(db)) { 
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    parseTaskLine(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Regex matching to store tasks into List
     * @param line Task from db
     */
    private void parseTaskLine(String line) {
        try {
            // Regex patterns for different task types
            String todoPattern = "\\[T\\]\\[([X ])\\]\\s*(.+)";
            String deadlinePattern = "\\[D\\]\\[([X ])\\]\\s*(.+?)\\s*\\(by:\\s*(.+?)\\)";
            String eventPattern = "\\[E\\]\\[([X ])\\]\\s*(.+?)\\s*\\(from:\\s*(.+?)\\s+to:\\s*(.+?)\\)";
            
            if (line.matches(todoPattern)) {  // Remove substring(2) - it was causing issues
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(todoPattern).matcher(line);
                if (matcher.find()) {
                    String status = matcher.group(1);
                    String task = matcher.group(2);
                    toDoList.addToDos(task);
                    if ("X".equals(status)) {
                        toDoList.markTask(toDoList.size());
                    }
                }
            } else if (line.matches(deadlinePattern)) {
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(deadlinePattern).matcher(line);
                if (matcher.find()) {
                    String status = matcher.group(1);
                    String task = matcher.group(2);
                    String deadline = matcher.group(3);
                    toDoList.addDeadline(task + " /by " + deadline);
                    if ("X".equals(status)) {
                        toDoList.markTask(toDoList.size());
                    }
                }
            } else if (line.matches(eventPattern)) {
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(eventPattern).matcher(line);
                if (matcher.find()) {
                    String status = matcher.group(1);
                    String task = matcher.group(2);
                    String from = matcher.group(3);
                    String to = matcher.group(4);
                    toDoList.addEvent(task + " /from " + from + " /to " + to);
                    if ("X".equals(status)) {
                        toDoList.markTask(toDoList.size());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing line: " + line + " - " + e.getMessage());
        }
    }

    /**
     * Creates a new DB 
     */
    private void createNewFile() {
        try {
            db.createNewFile();
            System.out.println("Created new task file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    /**
     * Save info into DB 
     */
    public void saveToFile() {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (int i = 0; i < toDoList.size(); i++) {
                fw.write(toDoList.getTask(i).toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    /**
     * Returns list object containing tasks loaded from db.
     * @return List object containing tasks
     */
    public List getToDoList() {
        return toDoList;
    }
}
