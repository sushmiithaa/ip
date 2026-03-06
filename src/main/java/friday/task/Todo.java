package friday.task;

/**
 * Represents a todo type task. A <code>Todo</code> object corresponds to
 * a task with a description and status of its completion.
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string to be printed when printing the todo in the application
     * @return string to print in the application
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string to be printed when adding/updating the todo in the file
     * @return string to add/update todo in the file
     */
    @Override
    public String printString() {
        return "T " + super.printString();
    }
}
