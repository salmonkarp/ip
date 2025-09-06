package taskmanager;

/**
 * General task class that can be further
 * expanded upon by its subclasses.
 */
public class Task {
    private final String name;
    private boolean isDone;

    /**
     * Constructor only using the task name/description.
     * Default behaviour is to set isDone to false as tasks
     * should generally not be done at point of creation.
     * @param name Name or description of task.
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Constructor specifying the task name and whether
     * it has been done or not. Mostly used for loading
     * from a file.
     * @param name Name or description of task.
     * @param isDone Whether the task has been completed or not.
     */
    protected Task(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }

    public String getName() {
        return this.name;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return (this.isDone ? "[X] " : "[ ] ") + this.name;
    }

    public String getSaveCode() {
        return "A";
    }

    public String getSaveString() {
        return getSaveCode()
                + TaskManager.SAVE_DELIMITER
                + this.name
                + TaskManager.SAVE_DELIMITER
                + this.isDone;
    }

}
