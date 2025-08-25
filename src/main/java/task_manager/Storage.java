package task_manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {

    private TaskList loadedTasks = new TaskList();

    public Storage(String filePath, Ui ui) {
        loadedTasks = new TaskList();
        try {
            File saveFile = new File(filePath);
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
            ui.printTasks(loadedTasks);
        } catch (Exception e) {
            System.out.println("Failed to obtain data in " + filePath);
            e.printStackTrace();
        }
    }

    public TaskList load() {
        return loadedTasks;
    }

    public void save(TaskList tasks, String filePath, Ui ui) {
        String stringifiedTasks = tasks.getTasksAsString();
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(stringifiedTasks);
            fileWriter.close();
            ui.printLine("Successfully wrote to " + filePath);
        } catch (IOException e) {
            ui.printLine("Failed to write to " + filePath);
            e.printStackTrace();
        }

    }
}
