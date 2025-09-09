package taskmanager;

/**
 * Command to save tasks to a local file
 */
public class SaveCommand extends Command {

    private final Storage storage;
    private final TaskList tasks;

    private SaveCommand(Storage storage, TaskList tasks) {
        assert storage != null && tasks != null;
        this.storage = storage;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        return storage.save(tasks);
    }

    protected static class Factory implements Command.Factory {

        @Override
        public String getPrefix() {
            return "save";
        }

        @Override
        public Command createFromUserInput(String userInput, TaskList tasks, Storage storage) {
            return new SaveCommand(storage, tasks);
        }
    }
}
