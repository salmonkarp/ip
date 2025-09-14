package taskbot;

import java.util.Comparator;

/**
 * Command to sort tasks based on various possible parameters
 */
public class SortCommand extends Command {

    private enum SortType {
        ALPHABETICAL,
        TIME_CREATED,
        IS_DONE,
        DEADLINE
    }

    private final TaskList tasks;
    private final SortType sortType;

    private SortCommand(TaskList tasks, SortType sortType) {
        assert sortType != null && tasks != null;
        this.tasks = tasks;
        this.sortType = sortType;
    }

    @Override
    public String execute() {
        switch(sortType) {
        case ALPHABETICAL -> tasks.sort(Task::compareTo);
        case TIME_CREATED -> tasks.sort(Comparator.comparing(Task::getTimeCreated));
        case IS_DONE -> tasks.sort(Comparator.comparing(Task::isDone).thenComparing(Task::getName));
        case DEADLINE -> tasks.sort(Comparator.comparing(t -> {
            if (t instanceof DeadlineTask d) {
                return d.getDeadline();
            }
            if (t instanceof EventTask e) {
                return e.getStartTime();
            }
            return null;
        }, Comparator.nullsLast(Comparator.naturalOrder())));
        default -> throw new IllegalStateException("Unexpected value: " + sortType);
        }
        return ("I've sorted your tasks.\n") + tasks.toString();
    }

    protected static class Factory implements Command.Factory {

        private static final String PREFIX = "sort";

        @Override
        public String getPrefix() {
            return PREFIX;
        }

        @Override
        public Command createFromUserInput(String userInput, TaskList tasks, Storage storage) {
            String trimmedInput = userInput.substring(PREFIX.length()).trim();
            if (trimmedInput.equals("alphabetical") || trimmedInput.isEmpty()) {
                return new SortCommand(tasks, SortType.ALPHABETICAL);
            } else if (trimmedInput.equals("time_created")) {
                return new SortCommand(tasks, SortType.TIME_CREATED);
            } else if (trimmedInput.equals("is_done")) {
                return new SortCommand(tasks, SortType.IS_DONE);
            } else if (trimmedInput.equals("deadline")) {
                return new SortCommand(tasks, SortType.DEADLINE);
            } else {
                throw new IllegalArgumentException(
                        "Unrecognised sort parameter. Use alphabetical / time_created / is_done / deadline.");
            }
        }
    }
}
