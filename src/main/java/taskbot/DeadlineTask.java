package taskbot;

import static taskbot.Storage.SAVE_DELIMITER;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A type of task that has a deadline in the form of a date.
 */
public class DeadlineTask extends Task {

    private final LocalDateTime deadline;

    /**
     * Constructor for a DeadlineTask object, which is just a task
     * with a deadline.
     * @param name     The task name / description.
     * @param deadline A date in the form of LocalDate object.
     */
    public DeadlineTask(String name, LocalDateTime deadline) {
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
    public DeadlineTask(String name, boolean isDone, LocalDateTime deadline) {
        super(name, isDone);
        assert !name.isEmpty() && deadline != null;
        this.deadline = deadline;
    }

    /**
     * Constructor for a DeadlineTask object, which is just a task
     * with a deadline. Specifies whether the task has been done or not,
     * as well as the creation time.
     * @param name The task name / description.
     * @param isDone Whether the task has been done or not.
     * @param deadline A date in the form of LocalDate object.
     * @param timeCreated Time task was created.
     */
    public DeadlineTask(String name, boolean isDone, LocalDateTime timeCreated, LocalDateTime deadline) {
        super(name, isDone, timeCreated);
        assert !name.isEmpty() && deadline != null;
        this.deadline = deadline;
    }

    protected LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        String format = (deadline.getHour() == 0 && deadline.getMinute() == 0)
                ? "MMM d yyyy"
                : "MMM d yyyy HH:mm";
        return "[" + getSaveCode() + "]" + super.toString() + " (by: "
                + deadline.format(DateTimeFormatter.ofPattern(format)) + ")";
    }

    @Override
    public String getSaveCode() {
        return "D";
    }

    @Override
    public String getSaveString() {
        return super.getSaveString()
                + SAVE_DELIMITER
                + this.deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
