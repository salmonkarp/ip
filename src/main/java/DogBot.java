import java.util.Scanner;

public class DogBot {

    public static Scanner textScanner = new Scanner(System.in);
    public static String[] dogStrings = new String[100];
    public static Integer dogIndex = 0;

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

    public static void printDogStrings() {
        String result = "";
        for (Integer i = 0; i < dogIndex; i += 1) {
            result += ((i + 1) + ". " + dogStrings[i] + "\n");
        }
        System.out.println(addEnclosingLines(result));
    }

    public static void main(String[] args) {
        printInitialMessage();
        String userInput = "";
        while (true) {
            System.out.print("Insert your input here: ");
            userInput = textScanner.nextLine();
            if (userInput.toLowerCase().equals("bye"))
                break;
            if (userInput.toLowerCase().equals("list")) {
                printDogStrings();
                continue;
            }
            // System.out.println(addEnclosingLines(userInput));

            dogStrings[dogIndex] = userInput;
            System.out.println(addEnclosingLines("added: " + userInput));
            dogIndex += 1;

        }
        System.out.println(addEnclosingLines("Bye. Hope to see you again soon!"));
    }
}
