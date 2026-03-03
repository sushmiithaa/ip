package friday;

import java.util.Scanner;
import java.util.ArrayList;

public class Friday {

    public static final int REQ_NUM_COMMAND_COMPONENTS = 2;
    public static boolean isList, isMark, isUnmark, isTodo , isEvent, isDeadline, isDelete;

    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    public Friday(String filePath){
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadData());
        } catch (FridayException e) {
            ui.showErrorMessage("file detect tasks");
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showGreeting();
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
                    tasks.addTask(commandComponents,false);
                    ui.printAddedTask(tasks);
                    if (tasks.isSuccessAdd){
                        storage.updateFile(tasks);
                    }
                }
                catch (FridayException e) {
                    ui.showErrorMessage("task");
                }
                break;
            case "mark":
                try {
                    tasks.markTask(command);
                    if (tasks.isSuccessMark){
                        storage.updateFile(tasks);
                    }
                } catch (FridayException e) {
                    ui.showErrorMessage("mark");
                }
                break;
            case "unmark":
                try {
                   tasks.unmarkTask(command);
                    if (tasks.isSuccessUnMark){
                        storage.updateFile(tasks);
                    }
                } catch (FridayException e) {
                    ui.showErrorMessage("unmark");
                }
                break;
            case "delete":
                try {
                    tasks.deleteTask(command);
                    if (tasks.isSuccessDelete){
                        storage.updateFile(tasks);
                    }
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

    public static void main(String[] args) {
        new Friday("data/friday.txt").run();
    }
}
