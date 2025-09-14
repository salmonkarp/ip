package taskbot;

/**
 * Command to find tasks that match a certain query
 */
public class FindCommand extends Command {

    private final String query;
    private final TaskList tasks;

    private FindCommand(String query, TaskList tasks) {
        assert !query.isEmpty() && tasks != null;
        this.query = query;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder("Here are the matching tasks in your list:\n");
        int counter = 1;
        for (int i = 0; i < tasks.size(); i += 1) {
            Task currentTask = tasks.get(i);
            if (currentTask.getName().contains(query)) {
                result.append(counter).append(". ").append(currentTask).append('\n');
                counter += 1;
            }
        }
        if (result.isEmpty()) {
            return "No such task found!";
        }
        return (result.toString());
    }

    protected static class Factory implements Command.Factory {

        private static final String PREFIX = "find";

        @Override
        public String getPrefix() {
            return PREFIX;
        }

        @Override
        public Command createFromUserInput(String userInput, TaskList tasks, Storage storage) {
            String query = userInput.substring(PREFIX.length()).trim();
            if (query.isEmpty()) {
                throw new IllegalArgumentException("Cannot find for empty string!");
            }
            return new FindCommand(query, tasks);
        }
    }
}
