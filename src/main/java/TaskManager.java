import java.time.LocalDate;

public class TaskManager {
    private Ui ui = new Ui();
    private Storage storage = new Storage(LOCAL_DATA_PATH, ui);
    private TaskList tasks = new TaskList(100);
    private static final String LOCAL_DATA_PATH = "./data/tasks.txt";

    private void addNormalTask(String userInput) {
        String taskNameToAdd = userInput.substring(3).strip();
        Task taskToAdd = new Task(taskNameToAdd);
        tasks.add(taskToAdd);
        ui.printWithLines("I've added a new task: " + taskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    private void addTodoTask(String userInput) {
        String todoNameToAdd = userInput.substring(4).strip();
        TodoTask todoTaskToAdd = new TodoTask(todoNameToAdd);
        tasks.add(todoTaskToAdd);
        ui.printWithLines("I've added a new todo task: " + todoTaskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    private void addDeadlineTask(String userInput) {
        String[] deadlineTaskDetails = userInput.substring(8).split("/");
        if (deadlineTaskDetails.length != 2) {
            ui.printWithLines("Wrong format! Type 'deadline [name] / [deadline]'");
            return;
        }
        try {
            LocalDate.parse(deadlineTaskDetails[1].strip());
        } catch (Exception e) {
            ui.printWithLines("Wrong format! Type 'deadline [name] / [deadline]'");
            return;
        }
        String deadlineNameToAdd = deadlineTaskDetails[0].strip();
        String deadlineTime = deadlineTaskDetails[1].strip();
        DeadlineTask deadlineTaskToAdd = new DeadlineTask(deadlineNameToAdd, LocalDate.parse(deadlineTime));
        tasks.add(deadlineTaskToAdd);
        ui.printWithLines("I've added a new deadline task: " + deadlineTaskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    private void addEventTask(String userInput) {
        String[] eventTaskDetails = userInput.substring(5).split("/");
        if (eventTaskDetails.length != 3) {
            ui.printWithLines("Wrong format! Type 'event [name] / [startTime] / [endTime]'");
            return;
        }
        try {
            LocalDate.parse(eventTaskDetails[1].strip());
            LocalDate.parse(eventTaskDetails[2].strip());
        } catch (Exception e) {
            ui.printWithLines("Wrong format! Type 'event [name] / [startTime] / [endTime]'");
            return;
        }
        String eventNameToAdd = eventTaskDetails[0].strip();
        String eventStartTime = eventTaskDetails[1].strip();
        String eventEndTime = eventTaskDetails[2].strip();
        EventTask eventTaskToAdd = new EventTask(eventNameToAdd,
                LocalDate.parse(eventStartTime),
                LocalDate.parse(eventEndTime));
        tasks.add(eventTaskToAdd);
        ui.printWithLines("I've added a new event task: " + eventTaskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    private void markAsDoneFromInputString(String userInput) {
        try {
            Integer taskIndexToMark = Integer.parseInt(userInput.substring(4).strip()) - 1;
            if (taskIndexToMark < 0 || taskIndexToMark >= tasks.size()) {
                ui.printWithLines("Index out of range. Are you sure you inputted the right index?");
            } else {
                tasks.get(taskIndexToMark).markDone();
                ui.printWithLines("Nice! I've marked this task as done:\n" + tasks.get(taskIndexToMark));
            }
        } catch (NumberFormatException e) {
            ui.printWithLines("Format error! Did you put a single number after 'mark'?");
        }

    }

    private void markAsUndoneFromInputString(String userInput) {
        try {
            Integer taskIndexToMark = Integer.parseInt(userInput.substring(6).strip()) - 1;
            if (taskIndexToMark < 0 || taskIndexToMark >= tasks.size()) {
                ui.printWithLines("Task was not found. Are you sure you inputted the right index?");
            } else {
                tasks.get(taskIndexToMark).markUndone();
                ui.printWithLines("Nice! I've unmarked this task as done:\n" + tasks.get(taskIndexToMark));
            }
        } catch (NumberFormatException e) {
            ui.printWithLines("Format error! Did you put a number after 'unmark'?");
        }

    }

    private void deleteTask(String userInput) {
        try {
            int taskIndexToDelete = Integer.parseInt(userInput.substring(6).strip()) - 1;
            if (taskIndexToDelete < 0 || taskIndexToDelete >= tasks.size()) {
                ui.printWithLines("Index out of range. Are you sure you inputted the right index?");
                return;
            }
            Task deletedTask = tasks.remove(taskIndexToDelete);
            ui.printWithLines("I've removed this task: " + deletedTask.toString()
                    + "\nYou have " + tasks.size() + " tasks now.");
        } catch (NumberFormatException e) {
            ui.printWithLines("Format error! Did you put a single number after 'delete'?");
        }
    }

    public TaskManager(String filePath) {
        ui.printGreetingMessage();
        tasks = storage.load();

        String userInput = "";
        Parser.TaskCommand userTaskCommand;

        while (true) {
            ui.print("\nInsert your input here: ");
            userInput = ui.getNextLine();
            ui.print("\n");

            if (userInput.contains(">")) {
                ui.printWithLines("Please don't include the character '>'!");
                continue;
            }

            userTaskCommand = Parser.TaskCommand.parseStringInput(userInput);
            switch (userTaskCommand) {
                case ADD:
                    addNormalTask(userInput);
                    break;
                case LIST:
                    ui.printTasks(tasks);
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
                    addDeadlineTask(userInput);
                    break;
                case ADD_EVENT:
                    addEventTask(userInput);
                    break;
                case DELETE:
                    deleteTask(userInput);
                    break;
                case BYE:
                    ui.printWithLines("Bye. Hope to see you again soon!");
                    storage.save(tasks, LOCAL_DATA_PATH, ui);
                    return;
                case UNKNOWN:
                    ui.printWithLines("Unknown command. Try again.");
                    break;
            }

        }
    }

    public static void main(String[] args) {
        new TaskManager(LOCAL_DATA_PATH);
    }
}
