package taskmanager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {

    private final LocalDate deadline;

    /**
     * Returns an DeadlineTask object, which is just a task
     * with a deadline.
     * 
     * @param name     The task name / description.
     * @param deadline A date in the form of LocalDate object.
     */
    public DeadlineTask(String name, LocalDate deadline) {
        super(name);
        this.deadline = deadline;
    }

    public DeadlineTask(String name, boolean isDone, LocalDate deadline) {
        super(name, isDone);
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
                + Parser.SAVE_DELIMITER
                + this.deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
