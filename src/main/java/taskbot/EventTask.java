package taskbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A type of task that has both a starting and ending time.
 */
public class EventTask extends Task {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    /**
     * Constructor to create an EventTask
     * @param name Name/description of task.
     * @param startTime When the event starts, in the form of a LocalDate object.
     * @param endTime When the event ends, in the form of a LocalDate object.
     */
    public EventTask(String name, LocalDateTime startTime, LocalDateTime endTime) {
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
    public EventTask(String name, boolean isDone, LocalDateTime startTime, LocalDateTime endTime) {
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
    public EventTask(String name, boolean isDone,
                     LocalDateTime timeCreated,
                     LocalDateTime startTime,
                     LocalDateTime endTime) {
        super(name, isDone, timeCreated);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected LocalDateTime getStartTime() {
        return this.startTime;
    }

    @Override
    public String toString() {
        String startTimeFormat = (startTime.getHour() == 0 && startTime.getMinute() == 0)
                ? "MMM d yyyy" : "MMM d yyyy HH:mm";
        String endTimeFormat = (endTime.getHour() == 0 && endTime.getMinute() == 0)
                ? "MMM d yyyy" : "MMM d yyyy HH:mm";
        return "[" + getSaveCode() + "]" + super.toString()
                + " (from: " + startTime.format(DateTimeFormatter.ofPattern(startTimeFormat))
                + " to: " + endTime.format(DateTimeFormatter.ofPattern(endTimeFormat)) + ")";
    }

    @Override
    public String getSaveCode() {
        return "E";
    }

    @Override
    public String getSaveString() {
        return super.getSaveString()
                + TaskManager.SAVE_DELIMITER
                + this.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                + TaskManager.SAVE_DELIMITER
                + this.endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
