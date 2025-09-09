package taskmanager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A type of task that has both a starting and ending time.
 */
public class EventTask extends Task {
    private final LocalDate startTime;
    private final LocalDate endTime;

    /**
     * Constructor to create an EventTask
     * @param name Name/description of task.
     * @param startTime When the event starts, in the form of a LocalDate object.
     * @param endTime When the event ends, in the form of a LocalDate object.
     */
    public EventTask(String name, LocalDate startTime, LocalDate endTime) {
        super(name);
        assert startTime != null && endTime != null;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructor to create an EventTask, specifying whether the task
     * has been done or not.
     * @param name Name/description of task.
     * @param isDone Whether the task has been done or not.
     * @param startTime When the event starts, in the form of a LocalDate object.
     * @param endTime When the event ends, in the form of a LocalDate object.
     */
    public EventTask(String name, boolean isDone, LocalDate startTime, LocalDate endTime) {
        super(name, isDone);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructor to create an EventTask, specifying whether the task
     * has been done or not, as well as creation time of the task.
     * @param name Name/description of task.
     * @param isDone Whether the task has been done or not.
     * @param startTime When the event starts, in the form of a LocalDate object.
     * @param endTime When the event ends, in the form of a LocalDate object.
     * @param timeCreated When the task was created
     */
    public EventTask(String name, boolean isDone, LocalDateTime timeCreated, LocalDate startTime, LocalDate endTime) {
        super(name, isDone, timeCreated);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected LocalDate getStartTime() {
        return this.startTime;
    }

    @Override
    public String toString() {
        return "[" + getSaveCode() + "]" + super.toString() + " (from: "
                + this.startTime.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: "
                + this.endTime.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + ")";
    }

    @Override
    public String getSaveCode() {
        return "E";
    }

    @Override
    public String getSaveString() {
        return super.getSaveString()
                + TaskManager.SAVE_DELIMITER
                + this.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + TaskManager.SAVE_DELIMITER
                + this.endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
