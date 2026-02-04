import java.util.Scanner;

public class Friday {

    public static final String LINE = "\t____________________________________________________________";
    public static final int MAX_NUM_TASKS = 100;
    private static Task[] tasks = new Task[MAX_NUM_TASKS];
    public static int commandCount = 0;

    public static void listTasks() {
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < commandCount; i++) {
            System.out.println("\t" + (i + 1) + "." + tasks[i]);
        }
    }

    public static void addTask(String taskType, String description) {
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

    private static void markTask(int taskIndex) {
        tasks[taskIndex - 1].markAsDone();
        System.out.println("\tNice! I've marked this task as done:");
    }

    private static void unmarkTask(int taskIndex) {
        tasks[taskIndex - 1].setDone(false);
        System.out.println("\tOK, I've marked this task as not done yet:");
    }

    public static void main(String[] args) {
        System.out.println(LINE + "\n\t Hello! I'm Friday\n\t What can I do for you?\n" + LINE + "\n");
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        boolean isMark;
        boolean isUnmark;
        while (! command.equals("bye")) {
            System.out.println(LINE);
            isMark = command.startsWith("mark");
            isUnmark = command.startsWith("unmark");
            if (command.equals("list")) {
                listTasks();
            } else if (isMark || isUnmark) {
                int taskIndex = Integer.parseInt(command.split(" ")[1]);
                if (isMark) {
                    markTask(taskIndex);
                } else {
                    unmarkTask(taskIndex);
                }
                System.out.println("\t  " + tasks[taskIndex - 1]);

            } else {
                String taskType = command.split(" ")[0];
                String description = command.split(" ",2)[1];
                addTask(taskType,description);
                printAddedTask();
            }
            System.out.println(LINE + "\n");
            command = in.nextLine();
        }
        System.out.println(LINE + "\n\tBye. Hope to see you again soon!\n" + LINE);
    }
}
