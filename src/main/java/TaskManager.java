import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {

    public static Scanner textScanner = new Scanner(System.in);
    public static List<Task> taskArray = new ArrayList<Task>(100);

    public static enum TaskCommand {
        ADD,
        LIST,
        MARK_AS_DONE,
        UNMARK_AS_DONE,
        BYE,
        UNKNOWN;

        public static TaskCommand parseStringInput(String input) {
            if (input.startsWith("add "))
                return TaskCommand.ADD;
            else if (input.equals("list"))
                return TaskCommand.LIST;
            else if (input.startsWith("mark "))
                return TaskCommand.MARK_AS_DONE;
            else if (input.startsWith("unmark "))
                return TaskCommand.UNMARK_AS_DONE;
            else if (input.equals("bye"))
                return TaskCommand.BYE;
            else
                return TaskCommand.UNKNOWN;
        }
    }

    public static void printGreetingMessage() {
        String greetingMessage = """
                Hello! I'm TaskManager.
                What can I do for you?""";
        ;
        PrintUtil.printWithLines(greetingMessage);
    }

    public static void printTasks() {
        String result = "Here are the tasks in your list!\n";
        for (Integer i = 0; i < taskArray.size(); i += 1) {
            result += ((i + 1) + ". " + taskArray.get(i).toString() + "\n");
        }
        PrintUtil.printWithLines(result);
    }

    public static void main(String[] args) {
        printGreetingMessage();

        String userInput = "";
        TaskCommand userTaskCommand;

        while (true) {
            System.out.print("Insert your input here: ");
            userInput = textScanner.nextLine();
            userTaskCommand = TaskCommand.parseStringInput(userInput);
            switch (userTaskCommand) {
                case ADD:
                    String taskNameToAdd = userInput.substring(3).strip();
                    taskArray.add(new Task(taskNameToAdd));
                    PrintUtil.printWithLines("I've added a new task: " + taskNameToAdd);
                    break;
                case LIST:
                    printTasks();
                    break;
                case MARK_AS_DONE:
                    String taskNameToMark = userInput.substring(5).strip();
                    Task foundTaskToMark = null;
                    for (Task task : taskArray) {
                        if (task.getName().equals(taskNameToMark)) {
                            foundTaskToMark = task;
                            task.markDone();
                            break;
                        }
                    }
                    if (foundTaskToMark != null)
                        PrintUtil.printWithLines("Nice! I've marked this task as done:\n" + foundTaskToMark);
                    else
                        PrintUtil.printWithLines("Task was not found. Are you sure you inputted the right name?");

                    break;
                case UNMARK_AS_DONE:
                    String taskNameToUnmark = userInput.substring(7).strip();
                    Task foundTasktoUnmark = null;
                    for (Task task : taskArray) {
                        if (task.getName().equals(taskNameToUnmark)) {
                            foundTasktoUnmark = task;
                            task.markUndone();
                            break;
                        }
                    }
                    if (foundTasktoUnmark != null)
                        PrintUtil.printWithLines("Nice! I've unmarked this task as done:\n" + foundTasktoUnmark);
                    else
                        PrintUtil.printWithLines("Task was not found. Are you sure you inputted the right name?");

                    break;
                case BYE:
                    PrintUtil.printWithLines("Bye. Hope to see you again soon!");
                    return;
                case UNKNOWN:
                    PrintUtil.printWithLines("Unknown command. Try again.");
                    break;
            }

        }
    }
}
