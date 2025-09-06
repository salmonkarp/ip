package taskmanager;

import java.time.LocalDate;

public class DeadlineCommand extends Command{

    private final String description;
    private final LocalDate time;
    private final TaskList tasks;

    public DeadlineCommand(String description, LocalDate time, TaskList tasks) {
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

    public static class Factory implements Command.Factory{

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
            LocalDate deadlineTime;
            try {
                deadlineTime = LocalDate.parse(deadlineTaskDetails[1].strip());
            } catch (Exception e) {
                throw new IllegalArgumentException("Error parsing date! Date should be in the format: YYYY-MM-DD");
            }
            return new DeadlineCommand(deadlineNameToAdd, deadlineTime, tasks);
        }

    }
}
