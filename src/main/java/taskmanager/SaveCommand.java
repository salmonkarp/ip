package taskmanager;

public class SaveCommand extends Command{

    private final Storage storage;
    private final TaskList tasks;

    public SaveCommand(Storage storage, TaskList tasks) {
        this.storage = storage;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        return storage.save(tasks);
    }

    public static class Factory implements Command.Factory {

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
