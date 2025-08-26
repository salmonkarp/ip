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
        String resultMessage = "Here are the tasks in your list!\n";
        for (int i = 0; i < tasks.size(); i += 1) {
            resultMessage += ((i + 1) + ". " + tasks.get(i).toString() + "\n");
        }
        return (resultMessage);
    }

}
