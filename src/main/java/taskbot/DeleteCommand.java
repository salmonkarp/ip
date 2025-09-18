package taskbot;

/**
 * Command to delete a certain task using a specified index
 */
public class DeleteCommand extends Command {

    private final int indexToDelete;
    private final TaskList tasks;

    private DeleteCommand(int indexToDelete, TaskList tasks) {
        assert indexToDelete >= 0 && tasks != null && indexToDelete < tasks.size();
        this.indexToDelete = indexToDelete;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        Task deletedTask = tasks.remove(indexToDelete);
        return ("I've removed this task: \n" + deletedTask.toString()
                + "\nYou have " + tasks.size() + " task(s) now.");
    }

    protected static class Factory implements Command.Factory {

        private static final String PREFIX = "delete";

        @Override
        public String getPrefix() {
            return PREFIX;
        }

        @Override
        public Command createFromUserInput(String userInput, TaskList tasks, Storage storage) {
            String rawIndexString = userInput.substring(PREFIX.length()).trim();
            int taskIndexToDelete;
            try {
                taskIndexToDelete = Integer.parseInt(rawIndexString) - 1;
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Please input a number! e.g. find 1");
            }
            if (taskIndexToDelete < 0 || taskIndexToDelete >= tasks.size()) {
                throw new IndexOutOfBoundsException("Index out of range. Are you sure you inputted the right index?");
            }
            return new DeleteCommand(taskIndexToDelete, tasks);
        }
    }
}
