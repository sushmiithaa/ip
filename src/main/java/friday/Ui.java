package friday;

import java.util.ArrayList;

public class Ui {

    public String LINE = "\t____________________________________________________________";

    public void listTasks(TaskList tasks) {
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < tasks.taskCount(); i++) {
            System.out.println("\t" + (i + 1) + "." + tasks.get(i));
        }
    }

    public void printAddedTask(ArrayList<Task> tasks) {
        int taskCount = tasks.size();
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks.get(taskCount - 1));
        System.out.println("\tNow you have " + taskCount + " task" + (taskCount != 1 ? "s" : "") + " in the list.");
    }

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

    public void showGreeting(){
        System.out.println(LINE + "\n\t Hello! I'm Friday\n\t What can I do for you?\n" + LINE + "\n");
    }

    public void printLineWithLineBreak(){
        System.out.println(LINE + "\n");
    }
    public void printLine(){
        System.out.println(LINE);
    }

    public void showExitMessage(){
        System.out.println(LINE + "\n\tBye. Hope to see you again soon!\n" + LINE);
    }

    public void showMarkedMessage(ArrayList<Task> tasks,int taskIndex){
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t  " + tasks.get(taskIndex));
    }

    public void showUnmarkedMessage(ArrayList<Task> tasks,int taskIndex){
        System.out.println("\tOK, I've marked this task as not done yet:");
        System.out.println("\t  " + tasks.get(taskIndex));
    }

    public void showDeleteMessage(ArrayList<Task> tasks,Task taskToBeRemoved){
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t  " + taskToBeRemoved);
        System.out.println("\tNow you have "+ tasks.size() + " task" + (tasks.size() != 1 ? "s" : ""));
    }
}
