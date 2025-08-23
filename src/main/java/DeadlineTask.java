public class DeadlineTask extends Task {

    private String deadline;

    public DeadlineTask(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    public DeadlineTask(String name, boolean isDone, String deadline) {
        super(name, isDone);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[" + getSaveCode() + "]" + super.toString() + " (by: " + deadline + ")";
    }

    @Override
    public String getSaveCode() {
        return "D";
    }

    @Override
    public String getSaveString() {
        return super.getSaveString() + '\0' + this.deadline;
    }
}
