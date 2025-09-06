package taskmanager;

public class DeleteCommand extends Command{

    private final int indexToDelete;
    private final TaskList tasks;

    public DeleteCommand(int indexToDelete, TaskList tasks) {
        this.indexToDelete = indexToDelete;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        Task deletedTask = tasks.remove(indexToDelete);
        return ("I've removed this task: " + deletedTask.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    public static class Factory implements Command.Factory {

        private final String PREFIX = "delete";

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
