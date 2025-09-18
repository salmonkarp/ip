package taskbot;

import static taskbot.Utility.parseTime;

import java.time.LocalDateTime;

/**
 * Command to create an event task.
 */
public class EventCommand extends Command {

    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final TaskList tasks;

    /**
     * Constructor to create a command whose job is to create an event task.
     * @param description Description of the task / event
     * @param startTime Starting time of the event
     * @param endTime Ending time of the event
     * @param tasks List of tasks for the task to be added to
     */
    public EventCommand(String description, LocalDateTime startTime, LocalDateTime endTime, TaskList tasks) {
        assert !description.isEmpty() && startTime != null && endTime != null && tasks != null;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tasks = tasks;
    }

    @Override
    public String execute() {
        EventTask eventTaskToAdd = new EventTask(description,
                startTime,
                endTime
        );
        tasks.add(eventTaskToAdd);
        return ("I've added a new event task: " + eventTaskToAdd
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    protected static class Factory implements Command.Factory {

        private static final String PREFIX = "event";

        @Override
        public String getPrefix() {
            return PREFIX;
        }

        @Override
        public Command createFromUserInput(String userInput, TaskList tasks, Storage storage) {
            String[] eventCommandDetails = userInput.substring(PREFIX.length()).split("/");
            if (eventCommandDetails.length != 3) {
                throw new IllegalArgumentException("Wrong format! Type 'event [name] / [start time] / [end time]'");
            }
            String deadlineNameToAdd = eventCommandDetails[0].strip();
            LocalDateTime startTime;
            LocalDateTime endTime;
            try {
                startTime = parseTime(eventCommandDetails[1].strip());
                endTime = parseTime(eventCommandDetails[2].strip());
            } catch (Exception e) {
                throw new IllegalArgumentException("""
                        Wrong format! \
                        Times should be in the one of the following formats:
                        - yyyy-MM-dd HH:mm
                        - yyyy-MM-dd
                        - MMM d yyyy HH:mm
                        - MMM d yyyy""");
            }
            if (endTime.isBefore(startTime)) {
                throw new IllegalArgumentException("End time must be after the start time!");
            }
            return new EventCommand(deadlineNameToAdd, startTime, endTime, tasks);
        }
    }
}
