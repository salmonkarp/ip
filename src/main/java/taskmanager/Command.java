package taskmanager;

import java.util.List;

public abstract class Command {

    public static final List<Command.Factory> commandFactories = List.of(
            new TodoCommand.Factory(),
            new DeadlineCommand.Factory(),
            new DeadlineCommand.Factory(),
            new EventCommand.Factory(),
            new MarkCommand.Factory(),
            new UnmarkCommand.Factory(),
            new ListCommand.Factory(),
            new SaveCommand.Factory(),
            new FindCommand.Factory(),
            new DeleteCommand.Factory(),
            new ByeCommand.Factory()
    );

    public abstract String execute();

    public interface Factory {
        String getPrefix();
        Command createFromUserInput(String userInput, TaskList tasks, Storage storage);
    }
}
