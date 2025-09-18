package taskbot;

import static taskbot.Utility.parseTime;

import java.time.LocalDateTime;

/**
 * Command to create a deadline task
 */
public class DeadlineCommand extends Command {

    private final String description;
    private final LocalDateTime time;
    private final TaskList tasks;

    private DeadlineCommand(String description, LocalDateTime time, TaskList tasks) {
        assert !description.isEmpty() && time != null && tasks != null;
        this.description = description;
        this.time = time;
        this.tasks = tasks;
    }
    @Override
    public String execute() {
        DeadlineTask deadlineTaskToAdd = new DeadlineTask(description, time);
        tasks.add(deadlineTaskToAdd);
        return ("I've added a new deadline task: " + deadlineTaskToAdd
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    protected static class Factory implements Command.Factory {

        private static final String PREFIX = "deadline";

        @Override
        public String getPrefix() {
            return PREFIX;
        }

        @Override
        public Command createFromUserInput(String userInput, TaskList tasks, Storage storage) {
            String[] deadlineTaskDetails = userInput.substring(PREFIX.length()).split("/");
            if (deadlineTaskDetails.length != 2) {
                throw new IllegalArgumentException("Wrong no. of arguments! Type 'deadline [name] / [deadline]'");
            }
            String deadlineNameToAdd = deadlineTaskDetails[0].strip();
            LocalDateTime deadlineTime;
            try {
                System.out.println(deadlineTaskDetails[1].strip());
                deadlineTime = parseTime(deadlineTaskDetails[1].strip());
            } catch (Exception e) {
                throw new IllegalArgumentException("""
                        Wrong format! \
                        Times should be in the one of the following formats:
                        - yyyy-MM-dd HH:mm
                        - yyyy-MM-dd
                        - MMM d yyyy HH:mm
                        - MMM d yyyy""");
            }
            return new DeadlineCommand(deadlineNameToAdd, deadlineTime, tasks);
        }

    }
}
