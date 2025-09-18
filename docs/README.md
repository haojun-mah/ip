# Audrey User Guide

**Audrey** is a desktop task management application optimized for use via a Command Line Interface (CLI) while also providing the benefits of a Graphical User Interface (GUI). If you're comfortable typing commands, Audrey can help you manage your tasks more efficiently than traditional GUI applications.

---

## Quick Start

1. **Ensure you have Java 17 or above installed** on your computer.

2. **Download the latest `audrey.jar` file** from the releases section.

3. **Copy the file** to the folder you want to use as the home folder for Audrey.

4. **Open a command terminal**, navigate to the folder containing the jar file, and run the following command:
   ```bash
   java -jar audrey.jar
   ```

5. **A GUI similar to the one below should appear** in a few seconds. The app will start with an empty task list.

6. **Type commands in the command box** and press Enter to execute them. For example:
   - Type `help` and press Enter to see all available commands
   - Type `list` to activate list mode and view your tasks

7. **Try some example commands:**
   - `todo Read a book` : Adds a simple todo task
   - `deadline Submit report /by 2025-12-25` : Adds a task with a deadline
   - `list` : Shows all your current tasks
   - `mark 1` : Marks the first task as completed
   - `bye` : Exits the application

Refer to the [Features](#features) section below for details on each command.

---

## Features

### :information_source: Notes about the command format:

- **Words in UPPER_CASE are parameters** to be supplied by you.  
  e.g., in `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo Read a book`.

- **Items in square brackets are optional**.  
  e.g., `snooze NUMBER [DATE]` can be used as `snooze 1` or `snooze 1 2025-12-25`.

- **Dates must be in YYYY-MM-DD format**.  
  e.g., `2025-12-25` for December 25, 2025.

- **Task numbers are 1-indexed**.  
  The first task in your list is task number 1, the second is task number 2, etc.

- **Commands are case-sensitive**.  
  Use lowercase commands like `list`, `todo`, `deadline`, etc.

---

### Viewing help : `help`

Shows a comprehensive list of all available commands with examples.

**Format:** `help`

**Example:**
```
> help
Available Commands
==================

Task Management:
• list - Show all tasks
• todo <description> - Add a todo task
• deadline <description> /by <date> - Add task with deadline
• event <description> /from <date> /to <date> - Add an event
...
```

---

### Activating list mode : `list`

Activates the task management mode and displays all your current tasks.

**Format:** `list`

**What it does:**
- Switches Audrey to list mode for task management
- Shows all active (non-snoozed) tasks
- Must be run before using other task management commands

**Example:**
```
> list
To Do List Activated!

Here are the tasks in your list:
1.[T][ ] Read a book
2.[D][X] Submit report (by:2025-12-25)
3.[E][ ] Team meeting (from:2025-12-20 to:2025-12-20)
```

**Task format explanation:**
- `[T]` = Todo task, `[D]` = Deadline task, `[E]` = Event task
- `[ ]` = Not completed, `[X]` = Completed
- Numbers show the task index for use with other commands

---

### Adding a todo task: `todo`

Adds a simple todo task to your list.

**Format:** `todo DESCRIPTION`

**Examples:**
- `todo Read a book`
- `todo Buy groceries`
- `todo Call dentist`

**Sample output:**
```
> todo Read a book
Got it. I've added this task:
   [T][ ] Read a book
Now you have 1 tasks in the list.
```

---

### Adding a deadline: `deadline`

Adds a task with a specific deadline date.

**Format:** `deadline DESCRIPTION /by DATE`

- `DATE` must be in YYYY-MM-DD format
- The `/by` keyword is required

**Examples:**
- `deadline Submit assignment /by 2025-12-25`
- `deadline Pay bills /by 2025-11-30`
- `deadline Book flight /by 2025-10-15`

**Sample output:**
```
> deadline Submit assignment /by 2025-12-25
Got it. I've added this task:
   [D][ ] Submit assignment (by:2025-12-25)
Now you have 2 tasks in the list.
```

---

### Adding an event: `event`

Adds an event with start and end dates.

**Format:** `event DESCRIPTION /from START_DATE /to END_DATE`

- Both dates must be in YYYY-MM-DD format
- Both `/from` and `/to` keywords are required
- Start date must be before or equal to end date

**Examples:**
- `event Conference /from 2025-12-20 /to 2025-12-22`
- `event Vacation /from 2025-07-15 /to 2025-07-25`
- `event Meeting /from 2025-11-05 /to 2025-11-05`

**Sample output:**
```
> event Conference /from 2025-12-20 /to 2025-12-22
Got it. I've added this task:
   [E][ ] Conference (from:2025-12-20 to:2025-12-22)
Now you have 3 tasks in the list.
```

---

### Marking a task as done: `mark`

Marks a task as completed.

**Format:** `mark NUMBER`

- `NUMBER` refers to the task number shown in the task list

**Examples:**
- `mark 1` : Marks the first task as completed
- `mark 3` : Marks the third task as completed

**Sample output:**
```
> mark 1
Nice! I've marked this task as done!:
   [T][X] Read a book
```

---

### Marking a task as not done: `unmark`

Marks a previously completed task as not done.

**Format:** `unmark NUMBER`

**Examples:**
- `unmark 1` : Marks the first task as not completed
- `unmark 2` : Marks the second task as not completed

**Sample output:**
```
> unmark 1
Ok! I've marked this task as not done yet!:
   [T][ ] Read a book
```

---

### Finding tasks: `find`

Searches for tasks containing specific keywords.

**Format:** `find KEYWORD [MORE_KEYWORDS]`

- Search is case-insensitive
- Returns tasks containing any of the specified keywords
- Searches through task descriptions

**Examples:**
- `find book` : Finds tasks containing "book"
- `find assignment report` : Finds tasks containing either "assignment" or "report"

**Sample output:**
```
> find book
Here are the matching tasks in your list:
1.[T][ ] Read a book
2.[T][ ] Return library book
```

---

### Deleting a task: `delete`

Permanently removes a task from your list.

**Format:** `delete NUMBER`

- `NUMBER` refers to the task number shown in the task list
- This action cannot be undone

**Examples:**
- `delete 1` : Deletes the first task
- `delete 3` : Deletes the third task

**Sample output:**
```
> delete 2
Removing this task!
 [D][ ] Submit assignment (by:2025-12-25)
Now you have 2 task in your list!
```

---

### Snoozing tasks: `snooze`

Temporarily hides tasks from your active task list. Great for tasks you want to postpone.

#### View snoozable tasks
**Format:** `snooze`

Shows all tasks that can be snoozed (non-completed tasks).

#### Snooze a task forever
**Format:** `snooze NUMBER`

Snoozes a task indefinitely until you manually unsnooze it.

#### Snooze a task until a specific date
**Format:** `snooze NUMBER DATE`

Snoozes a task until the specified date, after which it automatically becomes active again.

**Examples:**
- `snooze` : Shows all snoozable tasks
- `snooze 1` : Snoozes task 1 forever
- `snooze 2 2025-12-01` : Snoozes task 2 until December 1, 2025

**Sample output:**
```
> snooze 1
Task snoozed forever:
   [T][ ] Read a book (snoozed forever)

> snooze 2 2025-12-01
Task snoozed until 2025-12-01:
   [D][ ] Submit assignment (snoozed until 2025-12-01)
```

---

### Unsnoozing tasks: `unsnooze`

Reactivates a snoozed task, making it visible in your active task list again.

**Format:** `unsnooze NUMBER`

**Examples:**
- `unsnooze 1` : Reactivates the first task
- `unsnooze 3` : Reactivates the third task

**Sample output:**
```
> unsnooze 1
Task unsnoozed:
   [T][ ] Read a book
```

---

### Exiting the program: `bye`

Exits the list mode. In GUI mode, close the window to fully exit the application.

**Format:** `bye`

**Sample output:**
```
> bye
To Do List Deactivated!
```

---

## Data Storage

### Automatic saving
Audrey automatically saves your tasks to a file called `audrey_db.txt` in the same folder as the application. There's no need to save manually - your data is preserved every time you make changes.

### Data file location
Your task data is stored in `audrey_db.txt` in the application's home folder. The file uses a human-readable format:

```
[T][ ] Read a book
[D][X] Submit assignment (by:2025-12-25)
[E][ ] Conference (from:2025-12-20 to:2025-12-22)
```

### Manual editing
:exclamation: **Caution:** While you can manually edit the `audrey_db.txt` file, incorrect formatting may cause Audrey to lose your data. It's recommended to:
1. **Always backup** the file before manual editing
2. **Follow the exact format** shown above
3. **Use the application commands** instead of manual editing when possible

---

## FAQ

**Q: How do I transfer my tasks to another computer?**  
A: Copy the `audrey_db.txt` file from your current Audrey folder to the new computer's Audrey folder.

**Q: What happens to snoozed tasks with past dates?**  
A: Tasks snoozed until a past date automatically become active again and will appear in your task list.

**Q: Can I use Audrey without the GUI?**  
A: Yes! Audrey is designed with CLI-first interaction. The GUI simply provides a visual interface for the same commands.

**Q: What date formats are supported?**  
A: Only YYYY-MM-DD format is supported (e.g., 2025-12-25 for December 25, 2025).

**Q: How many tasks can I store?**  
A: There's no practical limit to the number of tasks you can store in Audrey.

---

## Command Summary

| Action | Format & Examples |
|--------|-------------------|
| **Help** | `help` |
| **Activate list mode** | `list` |
| **Add todo** | `todo DESCRIPTION`<br/>e.g., `todo Read a book` |
| **Add deadline** | `deadline DESCRIPTION /by DATE`<br/>e.g., `deadline Submit report /by 2025-12-25` |
| **Add event** | `event DESCRIPTION /from DATE /to DATE`<br/>e.g., `event Meeting /from 2025-12-20 /to 2025-12-20` |
| **Mark as done** | `mark NUMBER`<br/>e.g., `mark 1` |
| **Mark as not done** | `unmark NUMBER`<br/>e.g., `unmark 1` |
| **Find tasks** | `find KEYWORD [MORE_KEYWORDS]`<br/>e.g., `find book assignment` |
| **Delete task** | `delete NUMBER`<br/>e.g., `delete 2` |
| **View snoozable tasks** | `snooze` |
| **Snooze forever** | `snooze NUMBER`<br/>e.g., `snooze 1` |
| **Snooze until date** | `snooze NUMBER DATE`<br/>e.g., `snooze 1 2025-12-01` |
| **Unsnooze task** | `unsnooze NUMBER`<br/>e.g., `unsnooze 1` |
| **Exit list mode** | `bye` |

---

*Happy task managing with Audrey! 🎯*