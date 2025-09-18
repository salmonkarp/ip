package taskbot;

import static taskbot.Utility.parseTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * General utility class to help read and write
 * from files stored in user's device.
 */
public class Storage {

    protected static final String SAVE_DELIMITER = "`";

    private TaskList loadedTasks;
    private final String filePath;

    /**
     * Helper class to allow for future instantiation.
     * Initialisation will read from filePath, but we
     * need to call load() to actually update the programme's
     * actual task list.
     * @param filePath Path of where data is stored in user's device.
     */
    public Storage(String filePath) {
        this.loadedTasks = new TaskList();
        this.filePath = filePath;
    }

    /**
     * Loads stored local file and deserializes the tasks
     * stored in it.
     * @return List of tasks obtained from local file
     */
    public TaskList load() {
        assert !filePath.isEmpty() && !filePath.startsWith("C:");
        try {
            File saveFile = new File(filePath);
            File parentDir = saveFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean isSuccessful = parentDir.mkdirs();
                System.out.println("Create new directories: " + (isSuccessful ? "" : "not") + "successful");
            }
            if (!saveFile.exists()) {
                boolean isSuccessful = saveFile.createNewFile();
                System.out.println("Create new save file: " + (isSuccessful ? "" : "not") + "successful");
            }
            Scanner scanner = new Scanner(saveFile);
            while (scanner.hasNextLine()) {
                String rawString = scanner.nextLine();
                Task task = getTaskFromSaveString(rawString);
                if (task == null) {
                    continue;
                }
                loadedTasks.add(task);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Failed to obtain data in " + filePath);
        }
        return loadedTasks;
    }

    /**
     * Saves tasks onto a local file in the user's device.
     * @param tasks List of tasks obtain from main TaskManager process.
     */
    public String save(TaskList tasks) {
        loadedTasks = tasks;
        assert tasks != null;
        String stringifiedTasks = tasks.getTasksAsString();
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(stringifiedTasks);
            fileWriter.close();
            return ("Successfully wrote to " + filePath);
        } catch (IOException e) {
            return ("Failed to write to " + filePath);
        }

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
                    parseTime(delimitedStrings[4])); // - deadline
        case "E" -> // Event task
            new EventTask(delimitedStrings[1], // - name
                    Boolean.parseBoolean(delimitedStrings[2]), // - isDone
                    LocalDateTime.parse(delimitedStrings[3]),
                    parseTime(delimitedStrings[4]), // - startTime
                    parseTime(delimitedStrings[5])); // - endTime
        default -> null;
        };
    }
}
