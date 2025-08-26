package taskmanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * General utility class to help read and write
 * from files stored in user's device.
 */
public class Storage {

    private TaskList loadedTasks = new TaskList();

    /**
     * Helper class to allow for future instantiation.
     * Initialisation will read from filePath, but we
     * need to call load() to actually update the programme's
     * actual task list.
     * @param filePath Path of where data is stored in user's device.
     */
    public Storage(String filePath) {
        loadedTasks = new TaskList();
        try {
            File saveFile = new File(filePath);
            File parentDir = saveFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean isSuccessful = parentDir.mkdirs();
            }
            if (!saveFile.exists()) {
                boolean isSuccessful = saveFile.createNewFile();
                System.out.println("No save file found. Created a new one at " + filePath);
            }
            Scanner scanner = new Scanner(saveFile);
            while (scanner.hasNextLine()) {
                String rawString = scanner.nextLine();
                Task task = Parser.getTaskFromSaveString(rawString);
                if (task == null) {
                    continue;
                }
                loadedTasks.add(task);
            }
            scanner.close();
            PrintHelper.getTaskListAsString(loadedTasks);
        } catch (Exception e) {
            System.out.println("Failed to obtain data in " + filePath);
            e.printStackTrace();
        }
    }

    public TaskList load() {
        return loadedTasks;
    }

    /**
     * Saves tasks onto a local file in the user's device.
     * @param tasks List of tasks obtain from main TaskManager process.
     * @param filePath Path of where data should be stored.
     */
    public String save(TaskList tasks, String filePath) {
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
}
