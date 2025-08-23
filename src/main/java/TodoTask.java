public class TodoTask extends Task {
    public TodoTask(String name) {
        super(name);
    }

    public TodoTask(String name, boolean isDone) {
        super(name, isDone);
    }

    @Override
    public String getSaveCode() {
        return "T";
    }

    @Override
    public String toString() {
        return "[" + getSaveCode() + "]" + super.toString();
    }

    @Override
    public String getSaveString() {
        return super.getSaveString();
    }
}
