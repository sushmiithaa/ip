package friday.ui;

import friday.task.Task;

import java.util.ArrayList;

/**
 * Text UI of the application.
 */
public class Ui {

    public String LINE = "\t____________________________________________________________";

    /**
     * Prints all the tasks.
     *
     * @param tasks all tasks.
     */
    public void listTasks(ArrayList<Task> tasks) {
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + "." + tasks.get(i));
        }
    }
    /**
     * Prints the recently added task.
     *
     * @param tasks all the tasks.
     */
    public void printAddedTask(ArrayList<Task> tasks) {
        int taskCount = tasks.size();
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks.get(taskCount - 1));
        System.out.println("\tNow you have " + taskCount + " task" + (taskCount != 1 ? "s" : "") + " in the list.");
    }

    /**
     * Prints the general message for find command.
     */
    public void showFoundTaskGeneralMessage(){
        System.out.println("\tHere are the matching tasks in your list:");
    }

    /**
     * Prints the task found with the matching keyword.
     *
     * @param task the task to be printed.
     * @param taskNumber the current number of task found with the matching keyword.
     */
    public void showFoundTasks(Task task, int taskNumber) {
        System.out.println("\t" + (taskNumber) + "." + task);
    }

    /**
     * Prints the no tasks found message when none of the tasks have the keyword,
     */
    public void showNoTaskFoundMessage(){
        System.out.println("\tNo matching tasks found.");
    }

    /**
     * Prints the error message based on the type of error.
     *
     * @param type type of error.
     */
    public void showErrorMessage(String type) {
        switch (type) {
        case "command":
            System.out.println("\tCommand is invalid.");
            break;
        case "task":
            System.out.println("\tDescription of task is empty/incorrect format.");
            break;
        case "mark":
            System.out.println("\tInvalid number. Task does not exist.\n\tUse the tasks numbers shown in the list to mark tasks.");
            break;
        case "unmark":
            System.out.println("\tInvalid number. Task does not exist.\n\tUse the tasks numbers shown in the list to unmark tasks.");
            break;
        case "delete":
            System.out.println("\tInvalid number. Task does not exist.\n\tUse the tasks numbers shown in the list to delete tasks.");
            break;
        case "find":
            System.out.println("\tIncorrect format.\n\tInclude a keyword to search the task description.");
            break;
        case "mark format":
            System.out.println("\tIncorrect format.\n\tUse the tasks numbers shown in the list to mark tasks.");
            break;
        case "unmark format":
            System.out.println("\tIncorrect format.\n\tUse the tasks numbers shown in the list to unmark tasks.");
            break;
        case "delete format":
            System.out.println("\tIncorrect format.\n\tUse the tasks numbers shown in the list to delete tasks.");
            break;
        case "io":
            System.out.println("Something went wrong");
            break;
        case "file detect tasks":
            System.out.println("\tUnable to detect the tasks in the text file");
            break;
        default:
            System.out.println("\t Error has occured.");
            break;
        }
    }
    /**
     * Prints the greeting message.
     */
    public void showGreeting(){
        System.out.println(LINE + "\n\t Hello! I'm Friday\n\t What can I do for you?\n" + LINE + "\n");
    }

    /**
     * Prints a line with a line break.
     */
    public void printLineWithLineBreak(){
        System.out.println(LINE + "\n");
    }

    /**
     * Prints a line.
     */
    public void printLine(){
        System.out.println(LINE);
    }

    /**
     * Prints the exit message.
     */
    public void showExitMessage(){
        System.out.println(LINE + "\n\tBye. Hope to see you again soon!\n" + LINE);
    }

    /**
     * Prints the marked task message.
     *
     * @param tasks all the tasks.
     * @param taskIndex the current task index from the list of tasks.
     */
    public void showMarkedMessage(ArrayList<Task> tasks,int taskIndex){
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t  " + tasks.get(taskIndex));
    }

    /**
     * Prints the unmark task message.
     *
     * @param tasks all the tasks.
     * @param taskIndex the current task index from the list of tasks.
     */
    public void showUnmarkedMessage(ArrayList<Task> tasks,int taskIndex){
        System.out.println("\tOK, I've marked this task as not done yet:");
        System.out.println("\t  " + tasks.get(taskIndex));
    }

    /**
     * Prints the delete task message.
     *
     * @param tasks all the tasks.
     * @param taskToBeRemoved task to be removed.
     */
    public void showDeleteMessage(ArrayList<Task> tasks,Task taskToBeRemoved){
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t  " + taskToBeRemoved);
        System.out.println("\tNow you have "+ tasks.size() + " task" + (tasks.size() != 1 ? "s" : "") + " in the list.");
    }
}
