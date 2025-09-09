package taskmanager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A type of task that has a deadline in the form of a date.
 */
public class DeadlineTask extends Task {

    private final LocalDate deadline;

    /**
     * Constructor for a DeadlineTask object, which is just a task
     * with a deadline.
     * @param name     The task name / description.
     * @param deadline A date in the form of LocalDate object.
     */
    public DeadlineTask(String name, LocalDate deadline) {
        super(name);
        this.deadline = deadline;
    }

    /**
     * Constructor for a DeadlineTask object, which is just a task
     * with a deadline. Specifies whether the task has been done or not.
     * @param name The task name / description.
     * @param isDone Whether the task has been done or not.
     * @param deadline A date in the form of LocalDate object.
     */
    public DeadlineTask(String name, boolean isDone, LocalDate deadline) {
        super(name, isDone);
        assert !name.isEmpty() && deadline != null;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[" + getSaveCode() + "]" + super.toString() + " (by: "
                + deadline.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    @Override
    public String getSaveCode() {
        return "D";
    }

    @Override
    public String getSaveString() {
        return super.getSaveString()
                + TaskManager.SAVE_DELIMITER
                + this.deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
