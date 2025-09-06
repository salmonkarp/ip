package taskmanager;

import java.time.LocalDate;

public class EventCommand extends Command{

    private final String description;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private final TaskList tasks;

    public EventCommand(String description, LocalDate startTime, LocalDate endTime, TaskList tasks) {
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

    public static class Factory implements Command.Factory{

        public static String PREFIX = "event";

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
            LocalDate startTime;
            LocalDate endTime;
            try {
                startTime = LocalDate.parse(eventCommandDetails[1].strip());
                endTime = LocalDate.parse(eventCommandDetails[2].strip());
            } catch (Exception e) {
                throw new IllegalArgumentException("Wrong format! Dates should be in the format: YYYY-MM-DD");
            }
            if (endTime.isBefore(startTime)) {
                throw new IllegalArgumentException("End time must be after the start time!");
            }
            return new EventCommand(deadlineNameToAdd, startTime, endTime, tasks);
        }
    }
}
