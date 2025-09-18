package taskbot;

/**
 * Command to list out all tasks
 */
public class ListCommand extends Command {

    private final TaskList tasks;

    private ListCommand(TaskList tasks) {
        assert tasks != null;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        if (tasks.isEmpty()) {
            return "You have no tasks right now.";
        }
        return "Here are the tasks in your list!\n" + tasks.toString();
    }

    protected static class Factory implements Command.Factory {

        private static final String PREFIX = "list";

        @Override
        public String getPrefix() {
            return PREFIX;
        }

        @Override
        public ListCommand createFromUserInput(String userInput, TaskList taskList, Storage storage) {
            return new ListCommand(taskList);
        }
    }
}
