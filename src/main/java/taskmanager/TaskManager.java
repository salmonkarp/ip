package taskmanager;

/**
 * A general task manager class that contains the overall
 * logic flow to how the program runs.
 */
public class TaskManager {

    protected static final String LOCAL_DATA_PATH = "./data/tasks.txt";

    private final Ui ui = new Ui();
    private Storage storage = new Storage(LOCAL_DATA_PATH, ui);
    private TaskList tasks = new TaskList();

    /**
     * Returns a TaskManager object.
     * This is made into a non-static class to allow later instantiation.
     * @param filePath Path where tasks are stored in user's device
     */
    public TaskManager(String filePath) {
        ui.printGreetingMessage();
        tasks = storage.load();

        String userInput = "";
        Parser.TaskCommand userTaskCommand;

        while (true) {
            ui.print("\nInsert your input here: ");
            userInput = ui.getNextLine();
            ui.print("\n");

            if (userInput.contains(Parser.SAVE_DELIMITER)) {
                ui.printWithLines("Please don't include the character '" + Parser.SAVE_DELIMITER + "'!");
                continue;
            }

            userTaskCommand = Parser.TaskCommand.getTaskCommandFromInput(userInput);
            switch (userTaskCommand) {
            case ADD:
                Parser.handleAddNormalTask(userInput, tasks, ui);
                break;
            case LIST:
                ui.printTasks(tasks);
                break;
            case MARK_AS_DONE:
                Parser.handleMarkAsDone(userInput, tasks, ui);
                break;
            case UNMARK_AS_DONE:
                Parser.handleMarkAsUndone(userInput, tasks, ui);
                break;
            case ADD_TODO:
                Parser.handleAddTodoTask(userInput, tasks, ui);
                break;
            case ADD_DEADLINE:
                Parser.handleAddDeadlineTask(userInput, tasks, ui);
                break;
            case ADD_EVENT:
                Parser.handleAddEventTask(userInput, tasks, ui);
                break;
            case DELETE:
                Parser.handleDeleteTask(userInput, tasks, ui);
                break;
            case BYE:
                ui.printWithLines("Bye. Hope to see you again soon!");
                storage.save(tasks, LOCAL_DATA_PATH, ui);
                return;
            case FIND:
                Parser.handleFindTask(userInput, tasks, ui);
                break;
            default:
                ui.printWithLines("Unknown command. Try again.");
                break;
            }

        }
    }

    /**
     * Entrance point to program.
     * @param args Unused arguments.
     */
    public static void main(String[] args) {
        new TaskManager(LOCAL_DATA_PATH);
    }
}
