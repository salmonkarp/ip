package taskbot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
                    TaskManager.parseTime(delimitedStrings[4])); // - deadline
        case "E" -> // Event task
            new EventTask(delimitedStrings[1], // - name
                    Boolean.parseBoolean(delimitedStrings[2]), // - isDone
                    LocalDateTime.parse(delimitedStrings[3]),
                    TaskManager.parseTime(delimitedStrings[4]), // - startTime
                    TaskManager.parseTime(delimitedStrings[5])); // - endTime
        default -> null;
        };
    }

    public String getResponse(String userInput) {
        assert !userInput.isEmpty();
        if (userInput.contains(SAVE_DELIMITER)) {
            return ("Beep! Please don't include the character '" + SAVE_DELIMITER + "'!");
        }
        for (Command.Factory factory : Command.COMMAND_FACTORIES) {
            if (!userInput.startsWith(factory.getPrefix())) {
                continue;
            }
            Command command = factory.createFromUserInput(userInput, tasks, storage);
            return command.execute();
        }
        throw new IllegalArgumentException("Unrecognised command.");
    }

    /**
     * Helper method to parse deadlines in different formats.
     * @param input the raw date/time string
     * @return LocalDateTime parsed deadline
     */
    public static LocalDateTime parseTime(String input) {
        String[] patterns = {
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd",
            "MMM d yyyy HH:mm",
            "MMM d yyyy"
        };

        for (String p : patterns) {
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern(p);
                if (p.contains("HH")) {
                    return LocalDateTime.parse(input, format);
                } else {
                    return LocalDate.parse(input, format).atStartOfDay();
                }
            } catch (DateTimeParseException ignored) {
                // ignored as we try the next pattern,
                // and still throw an error if none match
            }
        }
        throw new IllegalArgumentException("Unrecognized deadline format: " + input);
    }

}
