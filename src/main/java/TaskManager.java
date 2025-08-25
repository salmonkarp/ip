import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    public static Scanner textScanner = new Scanner(System.in);
    public static List<Task> tasks = new ArrayList<Task>(100);
    public static final String LOCAL_DATA_PATH = "./data/tasks.txt";

    private static enum TaskCommand {
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

    private static String getTasksAsString() {
        String result = "";
        for (Task t : tasks) {
            result += t.getSaveString() + '\n';
        }
        return result;
    }

    private static void saveTasksToLocal() {
        String stringifiedTasks = getTasksAsString();
        try {
            FileWriter fileWriter = new FileWriter(LOCAL_DATA_PATH);
            fileWriter.write(stringifiedTasks);
            fileWriter.close();
            System.out.println("Successfully wrote to " + LOCAL_DATA_PATH);
        } catch (IOException e) {
            System.out.println("Failed to write to " + LOCAL_DATA_PATH);
            e.printStackTrace();
        }

    }

    private static void initializeTasks() {
        try {
            File saveFile = new File(LOCAL_DATA_PATH);
            Scanner scanner = new Scanner(saveFile);
            while (scanner.hasNextLine()) {
                String rawString = scanner.nextLine();
                Task task = getTaskFromSaveString(rawString);
                if (task == null) {
                    continue;
                }
                tasks.add(task);
            }
            scanner.close();
            PrintUtil.printTasks(tasks);
        } catch (Exception e) {
            System.out.println("Failed to obtain data in " + LOCAL_DATA_PATH);
            e.printStackTrace();
        }
    }

    private static Task getTaskFromSaveString(String s) {
        String[] delimitedStrings = s.split(">");
        String taskCode = delimitedStrings[0];
        if (taskCode.equals("A")) { // Normal task
            return new Task(delimitedStrings[1], Boolean.parseBoolean(delimitedStrings[2]));
        } else if (taskCode.equals("T")) { // Todo task
            return new TodoTask(delimitedStrings[1], Boolean.parseBoolean(delimitedStrings[2]));
        } else if (taskCode.equals("D")) { // Deadline task
            return new DeadlineTask(delimitedStrings[1], // name
                    Boolean.parseBoolean(delimitedStrings[2]), // isDone
                    LocalDate.parse(delimitedStrings[3])); // deadline
        } else if (taskCode.equals("E")) { // Event task
            return new EventTask(delimitedStrings[1], // name
                    Boolean.parseBoolean(delimitedStrings[2]), // isDone
                    LocalDate.parse(delimitedStrings[3]), // startTime
                    LocalDate.parse(delimitedStrings[4])); // endTime
        } else {
            return null;
        }
    }

    private static void addNormalTask(String userInput) {
        String taskNameToAdd = userInput.substring(3).strip();
        Task taskToAdd = new Task(taskNameToAdd);
        tasks.add(taskToAdd);
        PrintUtil.printWithLines("I've added a new task: " + taskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    private static void addTodoTask(String userInput) {
        String todoNameToAdd = userInput.substring(4).strip();
        TodoTask todoTaskToAdd = new TodoTask(todoNameToAdd);
        tasks.add(todoTaskToAdd);
        PrintUtil.printWithLines("I've added a new todo task: " + todoTaskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    private static void addDeadlineTask(String[] deadlineTaskDetails) {
        String deadlineNameToAdd = deadlineTaskDetails[0].strip();
        String deadlineTime = deadlineTaskDetails[1].strip();
        DeadlineTask deadlineTaskToAdd = new DeadlineTask(deadlineNameToAdd, LocalDate.parse(deadlineTime));
        tasks.add(deadlineTaskToAdd);
        PrintUtil.printWithLines("I've added a new deadline task: " + deadlineTaskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    private static void addEventTask(String[] eventTaskDetails) {
        String eventNameToAdd = eventTaskDetails[0].strip();
        String eventStartTime = eventTaskDetails[1].strip();
        String eventEndTime = eventTaskDetails[2].strip();
        EventTask eventTaskToAdd = new EventTask(eventNameToAdd,
                LocalDate.parse(eventStartTime),
                LocalDate.parse(eventEndTime));
        tasks.add(eventTaskToAdd);
        PrintUtil.printWithLines("I've added a new event task: " + eventTaskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    private static void markAsDoneFromInputString(String userInput) {
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

    private static void markAsUndoneFromInputString(String userInput) {
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
        PrintUtil.printGreetingMessage();
        initializeTasks();
        String userInput = "";
        TaskCommand userTaskCommand;

        while (true) {
            System.out.print("\nInsert your input here: ");
            userInput = textScanner.nextLine();
            System.out.print("\n");

            if (userInput.contains(">")) {
                PrintUtil.printWithLines("Please don't include the character '>'!");
                continue;
            }

            userTaskCommand = TaskCommand.parseStringInput(userInput);
            switch (userTaskCommand) {
                case ADD:
                    addNormalTask(userInput);
                    break;
                case LIST:
                    PrintUtil.printTasks(tasks);
                    break;
                case MARK_AS_DONE:
                    markAsDoneFromInputString(userInput);
                    break;
                case UNMARK_AS_DONE:
                    markAsUndoneFromInputString(userInput);
                    break;
                case ADD_TODO:
                    addTodoTask(userInput);
                    break;
                case ADD_DEADLINE:
                    String[] deadlineTaskDetails = userInput.substring(8).split("/");
                    if (deadlineTaskDetails.length != 2) {
                        PrintUtil.printWithLines("Wrong format! Type 'deadline [name] / [deadline]'");
                        break;
                    }
                    try {
                        LocalDate.parse(deadlineTaskDetails[1].strip());
                    } catch (Exception e) {
                        PrintUtil.printWithLines("Wrong format! Type 'deadline [name] / [deadline]'");
                        break;
                    }
                    addDeadlineTask(deadlineTaskDetails);
                    break;
                case ADD_EVENT:
                    String[] eventTaskDetails = userInput.substring(5).split("/");
                    if (eventTaskDetails.length != 3) {
                        PrintUtil.printWithLines("Wrong format! Type 'event [name] / [startTime] / [endTime]'");
                        break;
                    }
                    try {
                        LocalDate.parse(eventTaskDetails[1].strip());
                        LocalDate.parse(eventTaskDetails[2].strip());
                    } catch (Exception e) {
                        PrintUtil.printWithLines("Wrong format! Type 'event [name] / [startTime] / [endTime]'");
                        break;
                    }
                    addEventTask(eventTaskDetails);
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
                    saveTasksToLocal();
                    return;
                case UNKNOWN:
                    PrintUtil.printWithLines("Unknown command. Try again.");
                    break;
            }

        }
    }
}
