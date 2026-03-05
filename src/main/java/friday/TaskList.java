package friday;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;
    public int REQ_NUM_COMMAND_COMPONENTS = 2;
    private Ui ui = new Ui();
    private Parser parser = new Parser();
    public boolean isSuccessMark = false;
    public boolean isSuccessUnMark = false;
    public boolean isSuccessAdd = false;
    public boolean isSuccessDelete = false;

    public TaskList(ArrayList<Task> tasks){
        this.tasks = tasks;
    }

    public TaskList(){
        this.tasks = new ArrayList<>();
    }

    public void addTask(String[] commandComponents) {
        String taskType = null;
        String description = null;
        try {
            parser.checkForTaskDescription(commandComponents);
            taskType = commandComponents[0];
            description = commandComponents[1];
            switch (taskType) {
            case "todo":
                tasks.add(new Todo(description));
                ui.printAddedTask(tasks);
                isSuccessAdd = true;
                break;
            case "deadline":
                try {
                    tasks.add(parser.parseDeadline(description));
                    ui.printAddedTask(tasks);
                    isSuccessAdd = true;
                } catch (FridayException e) {
                    ui.showErrorMessage("task");
                }
                break;

            case "event":
                try {
                    tasks.add(parser.parseEvent(description));
                    ui.printAddedTask(tasks);
                    isSuccessAdd = true;
                } catch (FridayException e) {
                    ui.showErrorMessage("task");
                }
                break;
            }
        }  catch (FridayException e) {
            ui.showErrorMessage("task");
        }
    }

    public void markTask(String command) {
        String[] commandComponents = new String[REQ_NUM_COMMAND_COMPONENTS];
        try {
            commandComponents = parser.checkComponentSize(command);
            int taskIndex = parser.checkTaskIndex(commandComponents,tasks);
            tasks.get(taskIndex).markAsDone();
            ui.showMarkedMessage(tasks,taskIndex);
            isSuccessMark = true;
        } catch (FridayException e){
            ui.showErrorMessage("mark");
        } catch (NumberFormatException e) {
            ui.showErrorMessage("mark format");
        }
    }

    public void unmarkTask(String command) {
        String[] commandComponents = new String[REQ_NUM_COMMAND_COMPONENTS];
        try {
            commandComponents = parser.checkComponentSize(command);
            int taskIndex = parser.checkTaskIndex(commandComponents,tasks);
            tasks.get(taskIndex).setDone(false);
            ui.showUnmarkedMessage(tasks,taskIndex);
            isSuccessUnMark = true;
        } catch (FridayException e){
            ui.showErrorMessage("unmark");
        } catch (NumberFormatException e) {
            ui.showErrorMessage("unmark format");
        }
    }

    public void deleteTask(String command) {
        String[] commandComponents = new String[REQ_NUM_COMMAND_COMPONENTS];
        try {
            commandComponents = parser.checkComponentSize(command);
            int taskIndex = parser.checkTaskIndex(commandComponents,tasks);
            Task taskToBeRemoved = tasks.get(taskIndex);
            tasks.remove(taskIndex);
            ui.showDeleteMessage(tasks,taskToBeRemoved);
            isSuccessDelete = true;
        } catch (FridayException e){
            ui.showErrorMessage("delete");
        } catch (NumberFormatException e) {
            ui.showErrorMessage("delete format");
        }
    }

    public void findTask(String command) {
        String[] commandComponents = new String[REQ_NUM_COMMAND_COMPONENTS];
        try {
            commandComponents = parser.checkComponentSize(command);
            String taskToFind = commandComponents[1];
            int taskNumber = 0;
            int notFoundCounter = 0;
            ui.showFoundTaskGeneralMessage();
            for (Task task: tasks) {
                if (task.getDescription().contains(taskToFind)){
                    taskNumber++;
                    ui.showFoundTasks(task,taskNumber);
                } else {
                    notFoundCounter++;
                }
            }
            if (notFoundCounter == tasks.size()){
                ui.showNoTaskFoundMessage();
            }
        } catch (FridayException e) {
            ui.showErrorMessage("find");
        }
    }

    public int taskCount() {
        return tasks.size();
    }

    public Task get(int i) {
        return tasks.get(i);
    }
}
