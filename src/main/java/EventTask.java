import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {
    private LocalDate startTime;
    private LocalDate endTime;

    public EventTask(String name, LocalDate startTime, LocalDate endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventTask(String name, boolean isDone, LocalDate startTime, LocalDate endTime) {
        super(name, isDone);
        this.startTime = startTime;
        this.endTime = endTime;
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
                + Parser.SAVE_DELIMITER
                + this.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + Parser.SAVE_DELIMITER
                + this.endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
