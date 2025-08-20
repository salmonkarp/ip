import java.util.Scanner;

public class DogBot {

    public static Scanner textScanner = new Scanner(System.in);

    public static String addEnclosingLines(String input) {
        return """
                ____________________________________________________________
                """ + input + "\n" + """
                ____________________________________________________________
                """;
    }

    public static void printInitialMessage() {
        String greetingMessage = """
                Hello! I'm Dogbot.
                What can I do for you?
                               """;
        ;
        System.out.println(addEnclosingLines(greetingMessage));
    }

    public static void main(String[] args) {
        printInitialMessage();
        String userInput = "";
        while (true) {
            System.out.print("Insert your input here: ");
            userInput = textScanner.nextLine();
            if (userInput.toLowerCase().equals("bye"))
                break;
            System.out.println(addEnclosingLines(userInput));

        }
        System.out.println(addEnclosingLines("Bye. Hope to see you again soon!"));
    }
}
