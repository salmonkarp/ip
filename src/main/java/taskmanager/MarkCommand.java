package taskmanager;

/**
 * Command to mark a certain task as done
 */
public class MarkCommand extends Command {

    private final int indexToMark;
    private final TaskList tasks;

    private MarkCommand(int indexToMark, TaskList tasks) {
        this.indexToMark = indexToMark;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        tasks.get(indexToMark).markDone();
        return ("Nice! I've marked this task as done:\n" + tasks.get(indexToMark));
    }

    protected static class Factory implements Command.Factory {

        private static final String PREFIX = "mark";

        @Override
        public String getPrefix() {
            return PREFIX;
        }

        @Override
        public Command createFromUserInput(String userInput, TaskList tasks, Storage storage) {
            String rawIndexString = userInput.substring(PREFIX.length()).trim();
            int taskIndexToMark;
            try {
                taskIndexToMark = Integer.parseInt(rawIndexString) - 1;
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Please input a number! e.g. mark 1");
            }
            if (taskIndexToMark < 0 || taskIndexToMark >= tasks.size()) {
                throw new IndexOutOfBoundsException("Index out of range. Are you sure you inputted the right index?");
            } else {
                return new MarkCommand(taskIndexToMark, tasks);
            }
        }
    }
}
