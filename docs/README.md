# Friday user guide
Friday is a desktop app for managing 
different types of tasks, optimized for use via a Command Line 
Interface (CLI). If you can type fast, Friday can help you manage all your tasks in one application conveniently.
- [Quick Start](#quick-start)
- [Features](#features)

    - [Listing tasks: `list`](#listing-tasks-list)
    - [Adding a todo: `todo`](#adding-a-todo-todo)
    - [Adding a deadline: `deadline`](#adding-a-deadline-deadline)
    - [Adding an event: `event`](#adding-an-event-event)
    - [Marking a task: `mark`](#marking-a-task-mark)
    - [Unmarking a task: `unmark`](#unmarking-a-task-unmark)
    - [Deleting a task: `delete`](#deleting-a-task-delete)
    - [Finding a task: `find`](#finding-a-task-find)
- [Command Summary](#command-summary)

# Quick Start
1. Ensure you have Java 17 or above installed in your Computer.
2. Download the `.jar` file (ip.jar v0.2) from this link: https://github.com/sushmiithaa/ip/releases
3. Copy the file to the folder you want to use as the home folder for your Friday application. 
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar ip.jar` command to run the application.

# Features
## Listing tasks `list`

List all tasks stored in the application.

    ____________________________________________________________
    Here are the tasks in your list:
	1.[T][ ] borrow book
	2.[D][ ] return book (by: Sunday)
	3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
    ____________________________________________________________

Format: `list`

## Adding a todo `todo`
Adds a todo to the list of tasks.

    ____________________________________________________________
	Got it. I've added this task:
	  [T][ ] borrow book
	Now you have 1 task in the list.
	____________________________________________________________

Format: `todo TASKDESCRIPTION`

- The `TASKDESCRIPTION` must not be empty
- There must be a space between `todo` and `TASKDESCRIPTION`

Example:  
`todo borrow book`

## Adding a deadline `deadline`
Adds a deadline to the list of tasks.

    ____________________________________________________________
	Got it. I've added this task:
	  [D][ ] return book (by: Sunday)
	Now you have 2 tasks in the list.
	____________________________________________________________

Format: `deadline TASKDESCRIPTION /by DATETIME`
- The `TASKDESCRIPTION` and `DATETIME` must not be empty
- There must be a space between `deadline` and `TASKDESCRIPTION` and `/by` and `DATETIME`


Example:  
`deadline return book /by Sunday`

## Adding an event `event`
Adds an event to the list of tasks.

    ____________________________________________________________
	Got it. I've added this task:
	  [E][ ] project meeting (from: Mon 2pm to: 4pm)
	Now you have 3 tasks in the list.
	____________________________________________________________

Format: `event TASKDESCRIPTION /from STARTTIME /to ENDTIME`
- The `TASKDESCRIPTION` and `DATETIME` must not be empty
- There must be a space between `event` and `TASKDESCRIPTION` and `/from` and `STARTIME` `/to` and `ENDTIME`
- The event must follow the order: `/from STARTTIME` first then `/to ENDTIME`


Example:  
`event project meeting /from Mon 2pm /to 4pm`

## Marking a task `mark`
Marks a task to change its status to completed.

    ____________________________________________________________
    Nice! I've marked this task as done:
        [E][X] project meeting (from: Mon 2pm to: 4pm)
	____________________________________________________________

Format: `mark TASKINDEX`

- Marks the task at the specified `TASKINDEX`
- The index refers to the index number shown in the displayed task list.
- The index must be a positive integer found in the list
- There must be a space between `mark` and `TASKINDEX`

Example:  
`mark 1`

## Unmarking a task `unmark`
Unmarks a task to change its status to incomplete.

    ____________________________________________________________
    OK, I've marked this task as not done yet:
        [E][ ] project meeting (from: Mon 2pm to: 4pm)
	____________________________________________________________

Format: `unmark TASKINDEX`
- Unmarks the task at the specified `TASKINDEX`
- The index refers to the index number shown in the displayed task list.
- The index must be a positive integer found in the list
- There must be a space between `unmark` and `TASKINDEX`

Example:  
`unmark 1`

## Deleting a task `delete`
Deletes a task from the list of tasks.

    ____________________________________________________________
    Noted. I've removed this task:
	  [T][ ] borrow book
	Now you have 2 tasks in the list.
	____________________________________________________________

Format: `delete TASKINDEX`

- Deletes the task at the specified `TASKINDEX`
- The index refers to the index number shown in the displayed task list.
- The index must be a positive integer found in the list
- There must be a space between `delete` and `TASKINDEX`

Example:  
`delete 1`

## Finding a task `find`
Finds a task from the list of tasks based on the keyword.

    ____________________________________________________________
	Here are the matching tasks in your list:
	1.[T][ ] borrow book
	2.[D][ ] return book (by: Sunday)
	____________________________________________________________

Format: `find KEYWORD`
- The search is case-insensitive. e.g `book` will match `Book`
- Only the task description is searched, e.g `find todo` will not display all todos
- There must be a space between `find` and `KEYWORD`

Example:  
`find book`

# Command Summary
| Action | Format | Example |
|-------|-------|-------|
| **list** | `list` | `list` |
| **add todo** | `todo TASKDESCRIPTION` | `todo borrow book` |
| **add deadline** | `deadline TASKDESCRIPTION /by DATETIME` | `deadline return book /by Sunday` |
| **add event** | `event TASKDESCRIPTION /from STARTTIME /to ENDTIME` | `event project meeting /from Mon 2pm /to 4pm` |
| **mark task** | `mark TASKINDEX` | `mark 1` |
| **unmark task** | `unmark TASKINDEX` | `unmark 1` |
| **delete task** | `delete TASKINDEX` | `delete 1` |
| **find task** | `find KEYWORD` | `find book` |




