package taskmanager;

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
        StringBuilder resultMessage = new StringBuilder("Here are the tasks in your list!\n");
        for (int i = 0; i < tasks.size(); i += 1) {
            resultMessage.append((i + 1)).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return (resultMessage.toString());
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
