package friday.parser;

import friday.FridayException;
import friday.task.Deadline;
import friday.task.Event;
import friday.task.Task;

import java.util.ArrayList;

/**
 * Parses user input.
 */
public class Parser {
    public static final int REQ_NUM_COMMAND_COMPONENTS = 2;

    /**
     * Returns the command that has been split based on the type of information as an array of strings.
     *
     * @param command input entered by user.
     * @return command components.
     */
    public String[] parseCommand(String command){
        return command.split(" ", REQ_NUM_COMMAND_COMPONENTS);
    }

    /**
     * Returns Event object based on the components of the event command.
     *
     * @param description task description.
     * @return Event object.
     * @throws FridayException If all the necessary inputs (from time, to time, task description) does not exist in correct format or any of it is missing.
     */
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

    /**
     * Returns Deadline object based on the components of the deadline command.
     *
     * @param description task description.
     * @return Deadline object.
     * @throws FridayException If all the necessary inputs (by time, task description) does not exist in correct format or any of it is missing.
     */
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

    /**
     * Returns the components of the command as an array of strings if it meets the requirements.
     *
     * @param command input entered by user.
     * @return command components.
     * @throws FridayException If the number of components in the command is not 2.
     */
    public String[] checkComponentSize(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length != REQ_NUM_COMMAND_COMPONENTS) {
            throw new FridayException();
        }
        return commandComponents;
    }

    /**
     * Checks to see if the user input for find is valid and throws exception when it is invalid.
     *
     * @param command input entered by user.
     * @throws FridayException If the number of components in the command is smaller than 2
     */
    public String[] checkComponentSizeFind(String command) throws FridayException {
        String[] commandComponents = command.split(" ");
        if (commandComponents.length < REQ_NUM_COMMAND_COMPONENTS) {
            throw new FridayException();
        }
        return commandComponents;
    }

    /**
     * Checks to see if the task description is valid and throws exception when it is invalid.
     *
     * @param commandComponents command components.
     * @throws FridayException If the number of components in the command is smaller than 2 or the task description is empty.
     */
    public void checkForTaskDescription(String[] commandComponents) throws FridayException {
        if (commandComponents.length < REQ_NUM_COMMAND_COMPONENTS || commandComponents[1].isBlank()) {
            throw new FridayException();
        }
    }

    /**
     * Checks to see if the task index exists in the list of tasks.
     *
     * @param commandComponents all components of the command.
     * @param tasks all tasks.
     * @throws FridayException If the task index is larger than or equal to the total number of tasks or lesser than 0.
     */
    public int checkTaskIndex(String[] commandComponents, ArrayList<Task> tasks) throws FridayException {
        int taskIndex = Integer.parseInt(commandComponents[1]) - 1;
        if (taskIndex >= tasks.size() || taskIndex < 0) {
            throw new FridayException();
        }
        return taskIndex;
    }

    /**
     * Checks to see if the command entered is a valid command.
     *
     * @param command input entered by user.
     * @throws FridayException If the command is not one of the accepted commands.
     */
    public void findCommandType(String command) throws FridayException {
        boolean isList, isMark, isUnmark, isTodo , isEvent, isDeadline, isDelete,isFind;
        String commandType = parseCommand(command)[0];
        isList = commandType.equals("list");
        isMark = commandType.equals("mark");
        isUnmark = commandType.equals("unmark");
        isTodo = commandType.equals("todo");
        isEvent = commandType.equals("event");
        isDeadline = commandType.equals("deadline");
        isDelete = commandType.equals("delete");
        isFind = commandType.equals("find");
        boolean isNotValidCommand = !(isList || isMark || isUnmark || isTodo || isEvent || isDeadline || isDelete || isFind);
        if (isNotValidCommand) {
            throw new FridayException();
        }
    }
}
