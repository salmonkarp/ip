package taskmanager;

import java.util.List;

/**
 * General command class that needs to be performed
 * when user has inputted a correct input. Each subclass
 * of Command thus only stores the objects it requires to
 * execute the command.
 */
public abstract class Command {

    protected static final List<Command.Factory> COMMAND_FACTORIES = List.of(
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
            new SortCommand.Factory(),
            new ByeCommand.Factory()
    );

    /**
     * General function to execute what user intends, after
     * all error-checking has been completed
     * @return void
     */
    public abstract String execute();

    /**
     * Factory class inside each Command
     * whose job is to create the command based on
     * raw user input. This mainly exists to manage
     * the different argument lengths of different commands,
     * such that each command object truly only needs the
     * objects it requires to execute.
     */
    public interface Factory {
        String getPrefix();
        Command createFromUserInput(String userInput, TaskList tasks, Storage storage);
    }
}
