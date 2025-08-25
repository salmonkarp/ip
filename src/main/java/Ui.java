public class Ui {
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

    public static void printTasks(TaskList tasks) {
        String resultMessage = "Here are the tasks in your list!\n";
        for (Integer i = 0; i < tasks.size(); i += 1) {
            resultMessage += ((i + 1) + ". " + tasks.get(i).toString() + "\n");
        }
        printWithLines(resultMessage);
    }

    public static void printLine(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }
}
