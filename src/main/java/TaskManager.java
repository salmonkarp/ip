import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {

    public static Scanner textScanner = new Scanner(System.in);
    public static List<Task> tasks = new ArrayList<Task>(100);

    public static enum TaskCommand {
        ADD,
        LIST,
        MARK_AS_DONE,
        UNMARK_AS_DONE,
        ADD_TODO,
        ADD_DEADLINE,
        ADD_EVENT,
        DELETE,
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
            else if (input.startsWith("todo "))
                return TaskCommand.ADD_TODO;
            else if (input.startsWith("deadline "))
                return TaskCommand.ADD_DEADLINE;
            else if (input.startsWith("event "))
                return TaskCommand.ADD_EVENT;
            else if (input.startsWith("delete "))
                return TaskCommand.DELETE;
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
        String resultMessage = "Here are the tasks in your list!\n";
        for (Integer i = 0; i < tasks.size(); i += 1) {
            resultMessage += ((i + 1) + ". " + tasks.get(i).toString() + "\n");
        }
        PrintUtil.printWithLines(resultMessage);
    }

    public static void markAsDoneFromInputString(String userInput) {
        try {
            Integer taskIndexToMark = Integer.parseInt(userInput.substring(4).strip()) - 1;
            if (taskIndexToMark < 0 || taskIndexToMark >= tasks.size()) {
                PrintUtil.printWithLines("Index out of range. Are you sure you inputted the right index?");
            } else {
                tasks.get(taskIndexToMark).markDone();
                PrintUtil.printWithLines("Nice! I've marked this task as done:\n" + tasks.get(taskIndexToMark));
            }
        } catch (NumberFormatException e) {
            PrintUtil.printWithLines("Format error! Did you put a single number after 'mark'?");
        }

    }

    public static void markAsUndoneFromInputString(String userInput) {
        try {
            Integer taskIndexToMark = Integer.parseInt(userInput.substring(6).strip()) - 1;
            if (taskIndexToMark < 0 || taskIndexToMark >= tasks.size()) {
                PrintUtil.printWithLines("Task was not found. Are you sure you inputted the right index?");
            } else {
                tasks.get(taskIndexToMark).markUndone();
                PrintUtil.printWithLines("Nice! I've unmarked this task as done:\n" + tasks.get(taskIndexToMark));
            }
        } catch (NumberFormatException e) {
            PrintUtil.printWithLines("Format error! Did you put a number after 'unmark'?");
        }

    }

    public static void main(String[] args) {
        printGreetingMessage();

        String userInput = "";
        TaskCommand userTaskCommand;

        while (true) {
            System.out.print("\nInsert your input here: ");
            userInput = textScanner.nextLine();
            System.out.print("\n");
            userTaskCommand = TaskCommand.parseStringInput(userInput);
            switch (userTaskCommand) {
                case ADD:
                    String taskNameToAdd = userInput.substring(3).strip();
                    Task taskToAdd = new Task(taskNameToAdd);
                    tasks.add(taskToAdd);
                    PrintUtil.printWithLines("I've added a new task: " + taskToAdd.toString()
                            + "\nYou have " + tasks.size() + " tasks now.");
                    break;
                case LIST:
                    printTasks();
                    break;
                case MARK_AS_DONE:
                    markAsDoneFromInputString(userInput);
                    break;
                case UNMARK_AS_DONE:
                    markAsUndoneFromInputString(userInput);
                    break;
                case ADD_TODO:
                    String todoNameToAdd = userInput.substring(4).strip();
                    TodoTask todoTaskToAdd = new TodoTask(todoNameToAdd);
                    tasks.add(todoTaskToAdd);
                    PrintUtil.printWithLines("I've added a new todo task: " + todoTaskToAdd.toString()
                            + "\nYou have " + tasks.size() + " tasks now.");
                    break;
                case ADD_DEADLINE:
                    String[] deadlineTaskDetails = userInput.substring(8).split("/");
                    if (deadlineTaskDetails.length != 2) {
                        PrintUtil.printWithLines("Wrong format! Type 'deadline [name] / [deadline]'");
                        break;
                    }
                    String deadlineNameToAdd = deadlineTaskDetails[0].strip();
                    String deadlineTime = deadlineTaskDetails[1].strip();
                    DeadlineTask deadlineTaskToAdd = new DeadlineTask(deadlineNameToAdd, deadlineTime);
                    tasks.add(deadlineTaskToAdd);
                    PrintUtil.printWithLines("I've added a new deadline task: " + deadlineTaskToAdd.toString()
                            + "\nYou have " + tasks.size() + " tasks now.");
                    break;
                case ADD_EVENT:
                    String[] eventTaskDetails = userInput.substring(5).split("/");
                    if (eventTaskDetails.length != 3) {
                        PrintUtil.printWithLines("Wrong format! Type 'event [name] / [startTime] / [endTime]'");
                        break;
                    }
                    String eventNameToAdd = eventTaskDetails[0].strip();
                    String eventStartTime = eventTaskDetails[1].strip();
                    String eventEndTime = eventTaskDetails[2].strip();
                    EventTask eventTaskToAdd = new EventTask(eventNameToAdd, eventStartTime, eventEndTime);
                    tasks.add(eventTaskToAdd);
                    PrintUtil.printWithLines("I've added a new event task: " + eventTaskToAdd.toString()
                            + "\nYou have " + tasks.size() + " tasks now.");
                    break;
                case DELETE:
                    try {
                        int taskIndexToDelete = Integer.parseInt(userInput.substring(6).strip()) - 1;
                        if (taskIndexToDelete < 0 || taskIndexToDelete >= tasks.size()) {
                            PrintUtil.printWithLines("Index out of range. Are you sure you inputted the right index?");
                            break;
                        }
                        Task deletedTask = tasks.remove(taskIndexToDelete);
                        PrintUtil.printWithLines("I've removed this task: " + deletedTask.toString()
                                + "\nYou have " + tasks.size() + " tasks now.");
                    } catch (NumberFormatException e) {
                        PrintUtil.printWithLines("Format error! Did you put a single number after 'delete'?");
                    }

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
