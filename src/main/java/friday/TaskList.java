package friday;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;
    public int REQ_NUM_COMMAND_COMPONENTS = 2;
    private Ui ui = new Ui();
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
        isSuccessAdd = true;
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
            isSuccessMark = true;

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
            isSuccessUnMark = true;
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
            isSuccessDelete = true;
        } catch (NumberFormatException e) {
            ui.showErrorMessage("delete format");

        }
    }

    public int taskCount() {
        return tasks.size();
    }

    public Task get(int i) {
        return tasks.get(i);
    }
}
