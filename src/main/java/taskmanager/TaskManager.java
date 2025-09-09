package taskmanager;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A general task manager class that contains the overall
 * logic flow to how the program runs.
 */
public class TaskManager {

    protected static final String LOCAL_DATA_PATH = "./data/tasks.txt";
    protected static final String SAVE_DELIMITER = "`";

    private final Storage storage = new Storage(LOCAL_DATA_PATH);
    private final TaskList tasks;

    /**
     * Returns a TaskManager object.
     * This is made into a non-static class to allow later instantiation.
     */
    public TaskManager() {
        tasks = storage.load();
        assert tasks != null;
        System.out.println("TaskManager has been created");
    }

    public static Task getTaskFromSaveString(String s) {
        String[] delimitedStrings = s.split(SAVE_DELIMITER);
        String taskCode = delimitedStrings[0];
        return switch (taskCode) {
        case "A" -> // Normal task
            new Task(delimitedStrings[1],
                    Boolean.parseBoolean(delimitedStrings[2]),
                    LocalDateTime.parse(delimitedStrings[3]));
        case "T" -> // TodoTask
            new TodoTask(delimitedStrings[1],
                    Boolean.parseBoolean(delimitedStrings[2]),
                    LocalDateTime.parse(delimitedStrings[3]));
        case "D" -> // Deadline task
            new DeadlineTask(delimitedStrings[1], // - name
                    Boolean.parseBoolean(delimitedStrings[2]), // - isDone
                    LocalDateTime.parse(delimitedStrings[3]),
                    LocalDate.parse(delimitedStrings[4])); // - deadline
        case "E" -> // Event task
            new EventTask(delimitedStrings[1], // - name
                    Boolean.parseBoolean(delimitedStrings[2]), // - isDone
                    LocalDateTime.parse(delimitedStrings[3]),
                    LocalDate.parse(delimitedStrings[3]), // - startTime
                    LocalDate.parse(delimitedStrings[4])); // - endTime
        default -> null;
        };
    }

    public String getResponse(String userInput) {
        assert !userInput.isEmpty();
        if (userInput.contains(SAVE_DELIMITER)) {
            return ("Please don't include the character '" + SAVE_DELIMITER + "'!");
        }
        for (Command.Factory factory : Command.COMMAND_FACTORIES) {
            if (userInput.startsWith(factory.getPrefix())) {
                try {
                    Command command = factory.createFromUserInput(userInput, tasks, storage);
                    return command.execute();
                } catch (Exception e) {
                    return "Error! " + e.getMessage();
                }
            }
        }
        return "No matching command found. Try again?";
    }

}
