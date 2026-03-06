package friday.task;

/**
 * Represents an event type task. A <code>Event</code> object corresponds to
 * a task with a description, start time, end time and status of its completion.
 */
public class Event extends Task{

    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string to be printed when printing the event in the application.
     * @return string to print in the application.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns a string to be printed when adding/updating the event in the file.
     * @return string to add/update event in the file.
     */
    @Override
    public String printString() {
        return "E " + super.printString() + " | " + from + "-" + to;
    }
}
