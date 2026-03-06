package friday.task;

import friday.FridayException;
import friday.parser.Parser;
import friday.ui.Ui;

import java.util.ArrayList;

/**
 * Contains the task list and all operations related to tasks.
 */
public class TaskList {

    private ArrayList<Task> tasks;
    public int REQ_NUM_COMMAND_COMPONENTS = 2;
    public static final int REQ_COMMAND_COMPONENTS_LIST = 1;
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

    /**
     * List all tasks
     *
     * @param commandComponents contains all the portions of the command
     */
    public void listTasks(String[] commandComponents) {
        if (commandComponents.length == REQ_COMMAND_COMPONENTS_LIST) {
            ui.listTasks(tasks);
        } else {
            ui.showErrorMessage("command");
        }
    }

    /**
     * Adds tasks to the list of tasks.
     *
     * @param commandComponents contains all the portions of the command (task description, task type).
     */
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
    /**
     * Sets the status of the task to done.
     *
     * @param command input entered by user.
     */

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

    /**
     * Sets the status of the task to not done.
     *
     * @param command input entered by user.
     */
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

    /**
     * Removes the task from the list of tasks.
     *
     * @param command input entered by user.
     */
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

    /**
     * Checks if keyword exists in all the tasks and prints tasks with the keyword.
     *
     * @param command input entered by user.
     */
    public void findTask(String command) {
        String[] commandComponents = new String[REQ_NUM_COMMAND_COMPONENTS];
        try {
            commandComponents = parser.checkComponentSizeFind(command);
            String taskToFind = commandComponents[1];
            int taskNumber = 0;
            int notFoundCounter = 0;
            ui.showFoundTaskGeneralMessage();
            for (Task task: tasks) {
                if (task.getDescription().toLowerCase().contains(taskToFind.toLowerCase())){
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

    /**
     * Returns the numbers of tasks in the list of tasks.
     *
     * @return numbers of tasks.
     */
    public int taskCount() {
        return tasks.size();
    }

    /**
     * Returns the Task object found at the index in the ArrayList of Task objects.
     *
     * @param i index of the task represented by the Task object.
     * @return Task object.
     */
    public Task get(int i) {
        return tasks.get(i);
    }
}
