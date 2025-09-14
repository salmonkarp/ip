package taskbot;

import java.time.LocalDateTime;

/**
 * A type of task not too different from the normal Task class.
 */
public class TodoTask extends Task {
    public TodoTask(String name) {
        super(name);
    }

    public TodoTask(String name, boolean isDone) {
        super(name, isDone);
    }

    public TodoTask(String name, boolean isDone, LocalDateTime timeCreated) {
        super(name, isDone, timeCreated);
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
