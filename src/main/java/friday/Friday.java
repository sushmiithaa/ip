package friday;

import java.util.Scanner;

public class Friday {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private Parser parser;

    public Friday(String filePath){
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
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
                parser.findCommandType(command);
            } catch (FridayException e) {
                ui.showErrorMessage("command");
            }
            String commandType = parser.parseCommand(command)[0];
            switch (commandType){
            case "list":
                ui.listTasks(tasks);
                break;
            case "todo", "event", "deadline":
                String[] commandComponents = parser.parseCommand(command);
                tasks.addTask(commandComponents);
                if (tasks.isSuccessAdd){
                    storage.updateFile(tasks);
                }
                break;
            case "mark":
                tasks.markTask(command);
                if (tasks.isSuccessMark){
                    storage.updateFile(tasks);
                }
                break;
            case "unmark":
                tasks.unmarkTask(command);
                if (tasks.isSuccessUnMark){
                    storage.updateFile(tasks);
                }
                break;
            case "delete":
                tasks.deleteTask(command);
                if (tasks.isSuccessDelete){
                    storage.updateFile(tasks);
                }
                break;
            case "find":
                tasks.findTask(command);
                break;
            }
            ui.printLineWithLineBreak();
            command = in.nextLine().stripLeading();
        }
        ui.showExitMessage();
    }

    public static void main(String[] args) {
        new Friday("data/friday.txt").run();
    }
}
