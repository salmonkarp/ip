import java.util.List;

public final class PrintUtil {
    public static String addEnclosingLines(String input) {
        return """
                ____________________________________________________________
                """ + input + "\n" + """
                ____________________________________________________________
                """;
    }

    public static void printWithLines(String input) {
        System.out.println(addEnclosingLines(input));
    }

    public static void printGreetingMessage() {
        String greetingMessage = """
                Hello! I'm TaskManager.
                What can I do for you?""";
        ;
        printWithLines(greetingMessage);
    }

    public static void printTasks(List<Task> tasks) {
        String resultMessage = "Here are the tasks in your list!\n";
        for (Integer i = 0; i < tasks.size(); i += 1) {
            resultMessage += ((i + 1) + ". " + tasks.get(i).toString() + "\n");
        }
        printWithLines(resultMessage);
    }
}