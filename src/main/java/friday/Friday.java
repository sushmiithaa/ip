package friday;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void addTask(String[] commandComponents, boolean fromFile) throws FridayException {
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
            if (description.split(" /by ").length != 2 && !fromFile) {
                throw new FridayException();
            }
            String deadlineDescription = null;
            String dateTime = null;
            if (fromFile){
                deadlineDescription = description.split(" [|] ")[0];
                dateTime = description.split(" [|] ")[1];
            }
            else {
                deadlineDescription = description.split(" /by ")[0];
                dateTime = description.split(" /by ")[1];
            }

            tasks[commandCount] = new Deadline(deadlineDescription, dateTime);
            break;
        case "event":
            if (!fromFile) {
                boolean noFrom = description.split(" /from ").length != 2;
                if (noFrom) {
                    throw new FridayException();
                }
                boolean noTo = (description.split(" /from ")[1].split(" /to ")).length != 2;
                if (noTo) {
                    throw new FridayException();
                }
            }
            String eventDescription = null;
            String eventFrom = null;
            String eventTo = null;
            if (fromFile){
                eventDescription = description.split(" [|] ")[0];
                eventFrom = description.split(" [|] ")[1].split("-")[0];
                eventTo = description.split(" [|] ")[1].split("-")[1];
            } else {
                eventDescription = description.split(" /from ")[0];
                eventFrom = description.split(" /from ")[1].split(" /to ")[0];
                eventTo = description.split(" /from ")[1].split(" /to ")[1];
            }

            tasks[commandCount] = new Event(eventDescription, eventFrom, eventTo);
            break;
        }
        commandCount++;
    }

    public static void printAddedTask() {
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks[commandCount - 1]);
        System.out.println("\tNow you have "+ commandCount + " task" + (commandCount != 1 ? "s" : "") + " in the list.");
        updateFile();
    }

    private static void markTask(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length != 2){
            throw new FridayException();
        }
        try {
            int taskIndex = Integer.parseInt(commandComponents[1]);
            if (taskIndex > commandCount || taskIndex < 1) {
                throw new FridayException();
            }
            tasks[taskIndex - 1].markAsDone();
            System.out.println("\tNice! I've marked this task as done:");
            System.out.println("\t  " + tasks[taskIndex - 1]);
            updateFile();
        } catch (NumberFormatException e) {
            System.out.println("\tIncorrect format.\n\tUse the tasks numbers shown in the list to mark tasks");
        }
    }

    private static void unmarkTask(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length != 2) {
            throw new FridayException();
        }
        try {
            int taskIndex = Integer.parseInt(commandComponents[1]);
            if (taskIndex > commandCount || taskIndex < 1) {
                throw new FridayException();
            }
            tasks[taskIndex - 1].setDone(false);
            System.out.println("\tOK, I've marked this task as not done yet:");
            System.out.println("\t  " + tasks[taskIndex - 1]);
            updateFile();
        } catch (NumberFormatException e) {
            System.out.println("\tIncorrect format.\n\tUse the tasks numbers shown in the list to unmark tasks");
        }
    }

    private static void updateFile() {
        try {
            FileWriter fw = new FileWriter("./data/friday.txt");
            for (int i = 0; i < commandCount; i++) {
                fw.write(tasks[i].printString() + "\n");
            }
            fw.close();
        } catch (IOException ex) {
            System.out.println("Something went wrong");
        }
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

    private static void createFile() {
        new File("./data").mkdirs();
        try {
            FileWriter fw = new FileWriter("./data/friday.txt");
            fw.write("Testing writing new file");
            fw.close();
        } catch (IOException ex) {
            System.out.println("Something went wrong");
        }
    }

    private static void loadData(){
        try {
            File f = new File("./data/friday.txt");
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String[] taskComponents = s.nextLine().split(" [|] ",3);
                String[] commandComponents = new String[2];
                switch (taskComponents[0]){
                case "T":
                    commandComponents[0] = "todo";
                    break;
                case "D":
                    commandComponents[0] = "deadline";
                    break;
                case "E":
                    commandComponents[0] = "event";
                    break;
                }
                commandComponents[1] = taskComponents[2];
                addTask(commandComponents,true);
                if (taskComponents[1].equals("1")){
                    tasks[commandCount-1].markAsDone();
                }
            }
        } catch (FileNotFoundException e) {
            createFile();
        } catch (FridayException e) {
            System.out.println("\tUnable to detect the tasks in the text file");
        }
    }


    public static void main(String[] args) {
        System.out.println(LINE + "\n\t Hello! I'm Friday\n\t What can I do for you?\n" + LINE + "\n");
        loadData();
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
            } else if (isMark || isUnmark) {
                try {
                    if (isMark) {
                        markTask(command);
                    } else {
                        unmarkTask(command);
                    }
                } catch (FridayException exception) {
                    System.out.println("\tInvalid number. Task does not exist.\n\tUse the tasks numbers shown in the list");
                }
            } else if (isTodo || isEvent || isDeadline) {
                String[] commandComponents = command.split(" ",2);
                try {
                    addTask(commandComponents,false);
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
