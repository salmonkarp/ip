package taskmanager;

public class ByeCommand extends Command{

    private final Storage storage;
    private final TaskList tasks;

    public ByeCommand(Storage storage, TaskList tasks) {
        this.storage = storage;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        String saveResult = storage.save(tasks);
        return (saveResult + "\nBye. Hope to see you again soon!");
    }

    public static class Factory implements Command.Factory {

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
