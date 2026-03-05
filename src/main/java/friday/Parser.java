package friday;

import java.util.ArrayList;

public class Parser {
    public static final int REQ_NUM_COMMAND_COMPONENTS = 2;

    public String[] parseCommand(String command){
        return command.split(" ", REQ_NUM_COMMAND_COMPONENTS);
    }

    public Event parseEvent(String description) throws FridayException {
        String eventDescription;
        String eventFrom;
        String eventTo;

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

        return new Event(eventDescription, eventFrom, eventTo);
    }

    public Deadline parseDeadline(String description) throws FridayException {
        if (description.split(" /by ").length != REQ_NUM_COMMAND_COMPONENTS) {
            throw new FridayException();
        }
        String deadlineDescription;
        String dateTime;
        String[] deadlineDetails = description.split(" /by ");
        deadlineDescription = deadlineDetails[0];
        dateTime = deadlineDetails[1];

        return new Deadline(deadlineDescription, dateTime);
    }

    public String[] checkDeleteMarkUnmark(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length != REQ_NUM_COMMAND_COMPONENTS) {
            throw new FridayException();
        }
        return commandComponents;
    }

    public void checkForTaskDescription(String[] commandComponents) throws FridayException {
        if (commandComponents.length < REQ_NUM_COMMAND_COMPONENTS || commandComponents[1].isBlank()) {
            throw new FridayException();
        }
    }

    public int checkTaskIndex(String[] commandComponents, ArrayList<Task> tasks) throws FridayException {
        int taskIndex = Integer.parseInt(commandComponents[1]) - 1;
        if (taskIndex >= tasks.size() || taskIndex < 0) {
            throw new FridayException();
        }
        return taskIndex;
    }

    public void findCommandType(String command) throws FridayException {
        boolean isList, isMark, isUnmark, isTodo , isEvent, isDeadline, isDelete;
        String commandType = parseCommand(command)[0];
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
}
