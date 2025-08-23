public class Task {
    private String name;
    private boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    public Task(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }

    public String getName() {
        return this.name;
    };

    public boolean isDone() {
        return this.isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return (this.isDone ? "[X] " : "[ ] ") + this.name;
    }

    public String getSaveCode() {
        return "A";
    }

    public String getSaveString() {
        return getSaveCode() + '\0' + this.name + '\0' + this.isDone;
    }

}
