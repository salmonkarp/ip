package taskmanager;

/**
 * Command to list out all tasks
 */
public class ListCommand extends Command {

    private final TaskList tasks;

    private ListCommand(TaskList tasks) {
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        return PrintHelper.getTaskListAsString(tasks);
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
