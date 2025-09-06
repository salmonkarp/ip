package taskmanager;

/**
 * Command to create a todo task
 */
public class TodoCommand extends Command {

    private final String description;
    private final TaskList tasks;

    private TodoCommand(String description, TaskList taskList) {
        this.description = description;
        this.tasks = taskList;
    }

    @Override
    public String execute() {
        TodoTask todoTaskToAdd = new TodoTask(this.description);
        tasks.add(todoTaskToAdd);
        return ("I've added a new todo task: " + todoTaskToAdd
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    protected static class Factory implements Command.Factory {

        private static final String PREFIX = "todo";

        @Override
        public String getPrefix() {
            return PREFIX;
        }

        @Override
        public Command createFromUserInput(String userInput, TaskList tasks, Storage storage) {
            String description = userInput.substring(PREFIX.length()).strip();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("No description found");
            }
            return new TodoCommand(description, tasks);
        }
    }
}
