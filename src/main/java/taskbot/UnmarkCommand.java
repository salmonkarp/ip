package taskbot;

/**
 * Command to mark a task as undone
 */
public class UnmarkCommand extends Command {
    private final int indexToMark;
    private final TaskList tasks;

    private UnmarkCommand(int indexToMark, TaskList tasks) {
        assert indexToMark >= 0 && tasks != null && indexToMark < tasks.size();
        this.indexToMark = indexToMark;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        tasks.get(indexToMark).markUndone();
        return ("Nice! I've marked this task as not done:\n" + tasks.get(indexToMark));
    }

    protected static class Factory implements Command.Factory {

        private static final String PREFIX = "unmark";

        @Override
        public String getPrefix() {
            return PREFIX;
        }

        @Override
        public Command createFromUserInput(String userInput, TaskList tasks, Storage storage) {
            String rawIndexString = userInput.substring(PREFIX.length()).trim();
            int taskIndexToUnmark;
            try {
                taskIndexToUnmark = Integer.parseInt(rawIndexString) - 1;
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Please input a number! e.g. unmark 1");
            }
            if (taskIndexToUnmark < 0 || taskIndexToUnmark >= tasks.size()) {
                throw new IndexOutOfBoundsException("Index out of range. Are you sure you inputted the right index?");
            } else {
                return new UnmarkCommand(taskIndexToUnmark, tasks);
            }
        }
    }
}
