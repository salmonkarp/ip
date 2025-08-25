package taskmanager;

import java.util.Scanner;

public class Ui {

    private Scanner textScanner;

    public Ui() {
        textScanner = new Scanner(System.in);
    }

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

    public void printGreetingMessage() {
        String greetingMessage = """
                Hello! I'm TaskManager.
                What can I do for you?""";
        ;
        printWithLines(greetingMessage);
    }

    public void printTasks(TaskList tasks) {
        String resultMessage = "Here are the tasks in your list!\n";
        for (Integer i = 0; i < tasks.size(); i += 1) {
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
