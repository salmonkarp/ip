package taskbot;

import static taskbot.Storage.SAVE_DELIMITER;

/**
 * A general task manager class that contains the overall
 * logic flow to how the program runs.
 */
public class TaskManager {

    protected static final String LOCAL_DATA_PATH = "./data/tasks.txt";

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

    public void clearData() {
        tasks.clear();
    }
}
