package taskbot;

/**
 * Command to save and exit the program.
 */
public class ByeCommand extends Command {

    private final Storage storage;
    private final TaskList tasks;

    private ByeCommand(Storage storage, TaskList tasks) {
        assert storage != null && tasks != null;
        this.storage = storage;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        String saveResult = storage.save(tasks);
        return (saveResult + "\nBye. Hope to see you again soon!");
    }

    protected static class Factory implements Command.Factory {

        @Override
        public String getPrefix() {
            return "bye";
        }

        @Override
        public Command createFromUserInput(String userInput, TaskList tasks, Storage storage) {
            return new ByeCommand(storage, tasks);
        }
    }
}
