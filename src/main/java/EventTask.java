public class EventTask extends Task {
    private String startTime;
    private String endTime;

    public EventTask(String name, String startTime, String endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventTask(String name, boolean isDone, String startTime, String endTime) {
        super(name, isDone);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[" + getSaveCode() + "]" + super.toString() + " (from: "
                + this.startTime + " to: " + this.endTime + ")";
    }

    @Override
    public String getSaveCode() {
        return "E";
    }

    @Override
    public String getSaveString() {
        return super.getSaveString() + '\0' + this.startTime + '\0' + this.endTime;
    }
}
