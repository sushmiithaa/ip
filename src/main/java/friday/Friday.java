package friday;

import java.util.Scanner;
import java.util.ArrayList;

public class Friday {

    public static final String LINE = "\t____________________________________________________________";
    public static final int REQ_NUM_COMMAND_COMPONENTS = 2;
    private static ArrayList<Task> tasks = new ArrayList<>();
    public static boolean isList, isMark, isUnmark, isTodo , isEvent, isDeadline, isDelete;

    public static void listTasks() {
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + "." + tasks.get(i));
        }
    }

    public static void addTask(String[] commandComponents) throws FridayException {
        if (commandComponents.length < REQ_NUM_COMMAND_COMPONENTS || commandComponents[1].isBlank()) {
            throw new FridayException();
        }
        String taskType = commandComponents[0];
        String description = commandComponents[1];
        switch (taskType) {
        case "todo":
            tasks.add(new Todo(description));
            break;
        case "deadline":
            String[] deadlineDetails = description.split(" /by ");
            if (description.split(" /by ").length != REQ_NUM_COMMAND_COMPONENTS) {
                throw new FridayException();
            }
            String deadlineDescription = deadlineDetails[0];
            String dateTime = deadlineDetails[1];

            tasks.add(new Deadline(deadlineDescription, dateTime));
            break;
        case "event":
            String[] eventDetails = description.split(" /from ");
            boolean noFrom = eventDetails.length != REQ_NUM_COMMAND_COMPONENTS;
            if (noFrom) {
                throw new FridayException();
            }
            String[] eventTimeDetails = eventDetails[1].split(" /to ");
            boolean noTo = eventTimeDetails.length != REQ_NUM_COMMAND_COMPONENTS;
            if (noTo) {
                throw new FridayException();
            }
            String eventDescription = eventDetails[0];
            String eventFrom = eventTimeDetails[0];
            String eventTo = eventTimeDetails[1];

            tasks.add(new Event(eventDescription, eventFrom, eventTo));
            break;
        }
    }

    public static void printAddedTask() {
        int taskCount = tasks.size();
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks.get(taskCount-1));
        System.out.println("\tNow you have "+ taskCount + " task" + (taskCount != 1 ? "s" : "") + " in the list.");
    }

    private static void markTask(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length != REQ_NUM_COMMAND_COMPONENTS){
            throw new FridayException();
        }
        try {
            int taskIndex = Integer.parseInt(commandComponents[1]) -1;
            if (taskIndex > tasks.size() || taskIndex < 0 || tasks.size() == 0) {
                throw new FridayException();
            }
            tasks.get(taskIndex).markAsDone();
            System.out.println("\tNice! I've marked this task as done:");
            System.out.println("\t  " + tasks.get(taskIndex));
        } catch (NumberFormatException e) {
            System.out.println("\tIncorrect format.\n\tUse the tasks numbers shown in the list to mark tasks");
        }
    }

    private static void unmarkTask(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length != REQ_NUM_COMMAND_COMPONENTS){
            throw new FridayException();
        }
        try {
            int taskIndex = Integer.parseInt(commandComponents[1]) - 1;
            if (taskIndex > tasks.size() || taskIndex < 0 || tasks.size() == 0) {
                throw new FridayException();
            }
            tasks.get(taskIndex).setDone(false);
            System.out.println("\tOK, I've marked this task as not done yet:");
            System.out.println("\t  " + tasks.get(taskIndex));
        } catch (NumberFormatException e) {
            System.out.println("\tIncorrect format.\n\tUse the tasks numbers shown in the list to unmark tasks");
        }
    }

    private static void deleteTask(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length != REQ_NUM_COMMAND_COMPONENTS){
            throw new FridayException();
        }
        try {
            int taskIndex = Integer.parseInt(commandComponents[1]) - 1;
            if (taskIndex > tasks.size() || taskIndex < 0 || tasks.size() == 0) {
                throw new FridayException();
            }
            Task taskToBeRemoved = tasks.get(taskIndex);
            tasks.remove(taskIndex);

            System.out.println("\tNoted. I've removed this task:");
            System.out.println("\t  " + taskToBeRemoved);
            System.out.println("\tNow you have "+ tasks.size() + " task" + (tasks.size() != 1 ? "s" : "") + " in the list.");
        } catch (NumberFormatException e) {
            System.out.println("\tIncorrect format.\n\tUse the tasks numbers shown in the list to delete tasks");
        }
    }

    private static void findCommandType(String command) throws FridayException {
        String commandType = command.split(" ", REQ_NUM_COMMAND_COMPONENTS)[0];
        isList = commandType.equals("list");
        isMark = commandType.equals("mark");
        isUnmark = commandType.equals("unmark");
        isTodo = commandType.equals("todo");
        isEvent = commandType.equals("event");
        isDeadline = commandType.equals("deadline");
        isDelete = commandType.equals("delete");
        boolean isNotValidCommand = !(isList || isMark || isUnmark || isTodo || isEvent || isDeadline || isDelete);
        if (isNotValidCommand){
            throw new FridayException();
        }
    }

    public static void main(String[] args) {
        System.out.println(LINE + "\n\t Hello! I'm Friday\n\t What can I do for you?\n" + LINE + "\n");
        Scanner in = new Scanner(System.in);
        String command = in.nextLine().stripLeading();
        while (! command.equals("bye")) {
            System.out.println(LINE);
            try {
                findCommandType(command);
            } catch (FridayException e) {
                System.out.println("\tCommand is invalid.");
            }
            if (isList) {
                listTasks();
            } else if (isMark || isUnmark || isDelete) {
                try {
                    if (isMark) {
                        markTask(command);
                    } else if (isUnmark) {
                        unmarkTask(command);
                    } else {
                        deleteTask(command);
                    }
                } catch (FridayException exception) {
                    System.out.println("\tInvalid number. Task does not exist.\n\tUse the tasks numbers shown in the list");
                }
            } else if (isTodo || isEvent || isDeadline) {
                String[] commandComponents = command.split(" ", REQ_NUM_COMMAND_COMPONENTS);
                try {
                    addTask(commandComponents);
                    printAddedTask();
                }
                catch (FridayException e) {
                    System.out.println("\tDescription of task is empty/incorrect format.");
                }
            }
            System.out.println(LINE + "\n");
            command = in.nextLine().stripLeading();
        }
        System.out.println(LINE + "\n\tBye. Hope to see you again soon!\n" + LINE);
    }
}
