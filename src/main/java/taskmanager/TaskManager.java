package taskmanager;

/**
 * A general task manager class that contains the overall
 * logic flow to how the program runs.
 */
public class TaskManager {

    protected static final String LOCAL_DATA_PATH = "./data/tasks.txt";

    private Storage storage = new Storage(LOCAL_DATA_PATH);
    private TaskList tasks = new TaskList();

    /**
     * Returns a TaskManager object.
     * This is made into a non-static class to allow later instantiation.
     */
    public TaskManager() {
        tasks = storage.load();
        System.out.println("TaskManager has been created");
    }

    public String getResponse(String userInput) {
        if (userInput.contains(Parser.SAVE_DELIMITER)) {
            return ("Please don't include the character '" + Parser.SAVE_DELIMITER + "'!");
        }

        Parser.TaskCommand userTaskCommand = Parser.TaskCommand.getTaskCommandFromInput(userInput);
        return switch (userTaskCommand) {
        case ADD -> Parser.handleAddNormalTask(userInput, tasks);
        case LIST -> PrintHelper.getTaskListAsString(tasks);
        case MARK_AS_DONE -> Parser.handleMarkAsDone(userInput, tasks);
        case UNMARK_AS_DONE -> Parser.handleMarkAsUndone(userInput, tasks);
        case ADD_TODO -> Parser.handleAddTodoTask(userInput, tasks);
        case ADD_DEADLINE -> Parser.handleAddDeadlineTask(userInput, tasks);
        case ADD_EVENT -> Parser.handleAddEventTask(userInput, tasks);
        case DELETE -> Parser.handleDeleteTask(userInput, tasks);
        case BYE -> {
            String saveResult = storage.save(tasks, LOCAL_DATA_PATH);
            yield (saveResult + "\nBye. Hope to see you again soon!");
        }
        case FIND -> Parser.handleFindTask(userInput, tasks);
        default -> ("Unknown command. Try again.");
        };
    }

}
