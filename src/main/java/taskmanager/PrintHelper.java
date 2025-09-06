package taskmanager;

/**
 * General utility class to help print text and
 * help get user input.
 */
public class PrintHelper {

    /**
     * Helper method to easily print any TaskList object.
     * @param tasks TaskList object containing list of tasks.
     */
    public static String getTaskListAsString(TaskList tasks) {
        StringBuilder resultMessage = new StringBuilder("Here are the tasks in your list!\n");
        for (int i = 0; i < tasks.size(); i += 1) {
            resultMessage.append((i + 1)).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return (resultMessage.toString());
    }

}
