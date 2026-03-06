package friday.task;

/**
 * Represents a deadline type task. A <code>Deadline</code> object corresponds to
 * a task with a description, deadline and status of its completion.
 */
public class Deadline extends Task{

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string to be printed when printing the deadline in the application.
     * @return string to print in the application.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Returns a string to be printed when adding/updating the deadline in the file.
     * @return string to add/update deadline in the file.
     */
    @Override
    public String printString() {
        return "D " + super.printString() + " | " + by;
    }
}
