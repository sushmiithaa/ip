package friday.task;

/**
 * Represents a task. A <code>Task</code> object corresponds to
 * a task with a description and status of its completion.
 */
public class Task {

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     * @return description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     * @param description description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the status of completion of the task.
     * @return status of completion of the task.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets the status of completion of the task.
     * @param isDone status of completion of the task.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns the icon for status of completion of the task.
     * @return icon for status of completion of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Sets the status of completion of the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Returns a string to be printed when printing the task in the application.
     * @return string to print in the application.
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }

    /**
     * Returns a string to be printed when adding/updating the task in the file.
     * @return string to add/update task in the file.
     */
    public String printString() {
        return "| " + (isDone ? "1" : "0") + " | " + description;
    }
}
