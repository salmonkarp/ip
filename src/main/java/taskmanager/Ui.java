package taskmanager;

import java.util.Scanner;

/**
 * General utility class to help print text and
 * help get user input.
 */
public class Ui {

    private Scanner textScanner;
    /**
     * Constructor of UI object, which is not a static class
     * as to enable instantiation later.
     */
    public Ui() {
        textScanner = new Scanner(System.in);
    }

    /**
     * Prints string with two lines above and below it.
     * @param input String that needs to be wrapped.
     * @return String wrapped with lines.
     */
    public String addEnclosingLines(String input) {
        return """
                ____________________________________________________________
                """ + input + "\n" + """
                ____________________________________________________________
                """;
    }

    public void printWithLines(String input) {
        System.out.println(addEnclosingLines(input));
    }

    /**
     * Prints the greeting message whenever the user
     * opens the program.
     */
    public void printGreetingMessage() {
        String greetingMessage = """
                Hello! I'm TaskManager.
                What can I do for you?""";
        ;
        printWithLines(greetingMessage);
    }

    /**
     * Helper method to easily print any TaskList object.
     * @param tasks TaskList object containing list of tasks.
     */
    public void printTasks(TaskList tasks) {
        String resultMessage = "Here are the tasks in your list!\n";
        for (int i = 0; i < tasks.size(); i += 1) {
            resultMessage += ((i + 1) + ". " + tasks.get(i).toString() + "\n");
        }
        printWithLines(resultMessage);
    }

    public void printLine(String s) {
        System.out.println(s);
    }

    public void print(String s) {
        System.out.print(s);
    }

    public String getNextLine() {
        return textScanner.nextLine();
    }


}
