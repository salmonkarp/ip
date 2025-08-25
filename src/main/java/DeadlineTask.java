import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {

    private LocalDate deadline;

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
                + '>'
                + this.deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
