import java.util.Scanner;

public class Friday {

    public static final String LINE = "\t____________________________________________________________";
    public static final int MAX_NUM_TASKS = 100;
    private static Task[] tasks = new Task[MAX_NUM_TASKS];
    public static int commandCount = 0;
    public static boolean isList, isMark, isUnmark, isTodo , isEvent, isDeadline;

    public static void listTasks() {
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < commandCount; i++) {
            System.out.println("\t" + (i + 1) + "." + tasks[i]);
        }
    }

    public static void addTask(String[] commandComponents) throws FridayException {
        if (commandComponents.length < 2 || commandComponents[1].isBlank()) {
            throw new FridayException();
        }
        String taskType = commandComponents[0];
        String description = commandComponents[1];
        switch (taskType) {
        case "todo":
            tasks[commandCount] = new Todo(description);
            break;
        case "deadline":
            tasks[commandCount] = new Deadline(description.split(" /by ")[0], description.split(" /by ")[1]);
            break;
        case "event":
            tasks[commandCount] = new Event(description.split(" /from ")[0],
                    description.split(" /from ")[1].split(" /to ")[0],
                    description.split(" /from ")[1].split(" /to ")[1]);
            break;
        }
        commandCount++;
    }


    public static void printAddedTask() {
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks[commandCount - 1]);
        System.out.println("\tNow you have "+ commandCount + " task" + (commandCount != 1 ? "s" : "") + " in the list.");
    }

    private static void markTask(int taskIndex) throws FridayException {
        if (taskIndex > commandCount || taskIndex < 1){
            throw new FridayException();
        }
        tasks[taskIndex - 1].markAsDone();
        System.out.println("\tNice! I've marked this task as done:");
    }

    private static void unmarkTask(int taskIndex) throws FridayException {
        if (taskIndex > commandCount || taskIndex < 1){
            throw new FridayException();
        }
        tasks[taskIndex - 1].setDone(false);
        System.out.println("\tOK, I've marked this task as not done yet:");
    }

    private static void findCommandType(String command) throws FridayException {
        isList = command.equals("list");
        isMark = command.startsWith("mark");
        isUnmark = command.startsWith("unmark");
        isTodo = command.startsWith("todo");
        isEvent = command.startsWith("event");
        isDeadline = command.startsWith("deadline");
        boolean isNotValidCommand = !(isList || isMark || isUnmark || isTodo || isEvent || isDeadline);
        if (isNotValidCommand){
            throw new FridayException();
        }
    }

    public static void main(String[] args) {
        System.out.println(LINE + "\n\t Hello! I'm Friday\n\t What can I do for you?\n" + LINE + "\n");
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        while (! command.equals("bye")) {
            System.out.println(LINE);
            try {
                findCommandType(command);
            } catch (FridayException e) {
                System.out.println("\tCommand is invalid.");
            }
            if (isList) {
                listTasks();
            } else if (isMark || isUnmark) {
                int taskIndex = Integer.parseInt(command.split(" ")[1]);
                try {
                    if (isMark) {
                        markTask(taskIndex);
                    } else {
                        unmarkTask(taskIndex);
                    }
                    System.out.println("\t  " + tasks[taskIndex - 1]);
                } catch (FridayException exception) {
                    System.out.println("\tInvalid number. Task does not exist.");
                }

            } else if (isTodo || isEvent || isDeadline) {
                String[] commandComponents = command.split(" ",2);
                try {
                    addTask(commandComponents);
                    printAddedTask();
                }
                catch (FridayException e) {
                    System.out.println("\tDescription of task is empty.");
                }
            }
            System.out.println(LINE + "\n");
            command = in.nextLine();
        }
        System.out.println(LINE + "\n\tBye. Hope to see you again soon!\n" + LINE);
    }
}
