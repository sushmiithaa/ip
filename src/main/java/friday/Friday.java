package friday;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Friday {

    public static final int REQ_NUM_COMMAND_COMPONENTS = 2;
    public static final int REQ_NUM_COMPONENTS_FILE = 3;
    private static ArrayList<Task> tasks = new ArrayList<>();
    public static boolean isList, isMark, isUnmark, isTodo , isEvent, isDeadline, isDelete;

    private Ui ui;

    public Friday(){
        ui = new Ui();
    }

    public void run() {
        ui.showGreeting();
        loadData();
        Scanner in = new Scanner(System.in);
        String command = in.nextLine().stripLeading();
        while (! command.equals("bye")) {
            ui.printLine();
            try {
                findCommandType(command);
            } catch (FridayException e) {
                ui.showErrorMessage("command");
            }
            String commandType = command.split(" ", REQ_NUM_COMMAND_COMPONENTS)[0];
            switch (commandType){
            case "list":
                ui.listTasks(tasks);
                break;
            case "todo", "event", "deadline":
                try {
                    String[] commandComponents = command.split(" ", REQ_NUM_COMMAND_COMPONENTS);
                    addTask(commandComponents,false);
                    ui.printAddedTask(tasks);
                    updateFile();
                }
                catch (FridayException e) {
                    ui.showErrorMessage("task");
                }
                break;
            case "mark":
                try {
                    markTask(command);
                } catch (FridayException e) {
                    ui.showErrorMessage("mark");
                }
                break;
            case "unmark":
                try {
                   unmarkTask(command);
                } catch (FridayException e) {
                    ui.showErrorMessage("unmark");
                }
                break;
            case "delete":
                try {
                    deleteTask(command);
                } catch (FridayException e) {
                    ui.showErrorMessage("delete");
                }
                break;
            }
            ui.printLineWithLineBreak();
            command = in.nextLine().stripLeading();
        }
        ui.showExitMessage();
    }

    public void addTask(String[] commandComponents, boolean fromFile) throws FridayException {
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
            if (description.split(" /by ").length != REQ_NUM_COMMAND_COMPONENTS && !fromFile) {
                throw new FridayException();
            }
            String deadlineDescription;
            String dateTime;

            if (fromFile) {
                String[] deadlineDetailsFromFile = description.split(" [|] ");
                deadlineDescription = deadlineDetailsFromFile[0];
                dateTime = deadlineDetailsFromFile[1];
            } else {
                String[] deadlineDetails = description.split(" /by ");
                deadlineDescription = deadlineDetails[0];
                dateTime = deadlineDetails[1];
            }

            tasks.add(new Deadline(deadlineDescription, dateTime));
            break;

        case "event":
            String eventDescription;
            String eventFrom;
            String eventTo;

            if (!fromFile) {
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

                eventDescription = eventDetails[0];
                eventFrom = eventTimeDetails[0];
                eventTo = eventTimeDetails[1];
            } else {
                String[] eventDetailsFromFile = description.split(" [|] ");
                String[] eventTimeDetailsFromFile = eventDetailsFromFile[1].split("-");

                eventDescription = eventDetailsFromFile[0];
                eventFrom = eventTimeDetailsFromFile[0];
                eventTo = eventTimeDetailsFromFile[1];
            }

            tasks.add(new Event(eventDescription, eventFrom, eventTo));
            break;
        }
    }

    public void markTask(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length != REQ_NUM_COMMAND_COMPONENTS) {
            throw new FridayException();
        }
        try {
            int taskIndex = Integer.parseInt(commandComponents[1]) -1;
            if (taskIndex >= tasks.size() || taskIndex < 0) {
                throw new FridayException();
            }
            tasks.get(taskIndex).markAsDone();
            ui.showMarkedMessage(tasks,taskIndex);
            updateFile();
        } catch (NumberFormatException e) {
            ui.showErrorMessage("mark format");
        }

    }

    public void unmarkTask(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length != REQ_NUM_COMMAND_COMPONENTS) {
            throw new FridayException();
        }
        try {
            int taskIndex = Integer.parseInt(commandComponents[1]) - 1;
            if (taskIndex >= tasks.size() || taskIndex < 0) {
                throw new FridayException();
            }
            tasks.get(taskIndex).setDone(false);
            ui.showUnmarkedMessage(tasks,taskIndex);
            updateFile();
        } catch (NumberFormatException e) {
            ui.showErrorMessage("unmark format");
        }
    }

    public void deleteTask(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length != REQ_NUM_COMMAND_COMPONENTS) {
            throw new FridayException();
        }
        try {
            int taskIndex = Integer.parseInt(commandComponents[1]) - 1;
            if (taskIndex >= tasks.size() || taskIndex < 0) {
                throw new FridayException();
            }
            Task taskToBeRemoved = tasks.get(taskIndex);
            tasks.remove(taskIndex);

            ui.showDeleteMessage(tasks,taskToBeRemoved);
            updateFile();
        } catch (NumberFormatException e) {
            ui.showErrorMessage("delete format");

        }
    }

    public void findCommandType(String command) throws FridayException {
        String commandType = command.split(" ", REQ_NUM_COMMAND_COMPONENTS)[0];
        isList = commandType.equals("list");
        isMark = commandType.equals("mark");
        isUnmark = commandType.equals("unmark");
        isTodo = commandType.equals("todo");
        isEvent = commandType.equals("event");
        isDeadline = commandType.equals("deadline");
        isDelete = commandType.equals("delete");
        boolean isNotValidCommand = !(isList || isMark || isUnmark || isTodo || isEvent || isDeadline || isDelete);
        if (isNotValidCommand) {
            throw new FridayException();
        }
    }

    private void updateFile() {
        String[] directories = System.getProperty("user.dir").split("\\\\");
        String currDirectory = directories[directories.length-1];
        FileWriter fw;
        try {
            if (currDirectory.equals("ip")){
                fw = new FileWriter("./data/friday.txt");
            } else {
                fw = new FileWriter("../data/friday.txt");
            }
            for (Task task : tasks) {
                fw.write(task.printString() + "\n");
            }
            fw.close();
        } catch (IOException ex) {
            ui.showErrorMessage("io");
        }
    }

    public void createFile() {
        String[] directories = System.getProperty("user.dir").split("\\\\");
        String currDirectory = directories[directories.length-1];
        if (currDirectory.equals("ip")) {
            new File("./data").mkdirs();
            try {
                FileWriter fw = new FileWriter("./data/friday.txt");
                fw.write("Testing writing new file");
                fw.close();
            } catch (IOException ex) {
                ui.showErrorMessage("io");
            }
        } else {
            new File("../data").mkdirs();
            try {
                FileWriter fw = new FileWriter("../data/friday.txt");
                fw.write("Testing writing new file");
                fw.close();
            } catch (IOException ex) {
                ui.showErrorMessage("io");
            }
        }
    }

    public void loadData() {
        try {
            String[] directories = System.getProperty("user.dir").split("\\\\");
            String currDirectory = directories[directories.length-1];
            File f;
            if (currDirectory.equals("ip")) {
                f = new File("./data/friday.txt");
            } else {
                f = new File("../data/friday.txt");
            }
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String[] taskComponents = s.nextLine().split(" [|] ",REQ_NUM_COMPONENTS_FILE);
                String[] commandComponents = new String[2];
                String taskType = taskComponents[0];
                String taskDescription = taskComponents[2];
                String taskDoneStatus = taskComponents[1];
                switch (taskType){
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
                commandComponents[1] = taskDescription;
                addTask(commandComponents,true);
                if (taskDoneStatus.equals("1")){
                    tasks.get(tasks.size()-1).markAsDone();
                }
            }
        } catch (FileNotFoundException e) {
            createFile();
        } catch (FridayException e) {
            ui.showErrorMessage("file detect tasks");
        }
    }


    public static void main(String[] args) {
        new Friday().run();
    }
}
