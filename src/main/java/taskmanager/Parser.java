package taskmanager;

import java.time.LocalDate;

/**
 * General utility file that handles user input.
 * Also contains implementation of TaskCommand used
 * in TaskManager to handle a limited set of user instructions.
 */
public class Parser {

    /**
     * Helper inner class that defines the limited set of
     * user instructions available.
     */
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

        public static TaskCommand getTaskCommandFromInput(String input) {
            if (input.startsWith("add ")) {
                return TaskCommand.ADD;
            } else if (input.equals("list")) {
                return TaskCommand.LIST;
            } else if (input.startsWith("mark ")) {
                return TaskCommand.MARK_AS_DONE;
            } else if (input.startsWith("unmark ")) {
                return TaskCommand.UNMARK_AS_DONE;
            } else if (input.equals("bye")) {
                return TaskCommand.BYE;
            } else if (input.startsWith("todo ")) {
                return TaskCommand.ADD_TODO;
            } else if (input.startsWith("deadline ")) {
                return TaskCommand.ADD_DEADLINE;
            } else if (input.startsWith("event ")) {
                return TaskCommand.ADD_EVENT;
            } else if (input.startsWith("delete ")) {
                return TaskCommand.DELETE;
            } else {
                return TaskCommand.UNKNOWN;
            }
        }

    }

    protected static final String SAVE_DELIMITER = "`";

    public static Task getTaskFromSaveString(String s) {
        String[] delimitedStrings = s.split(SAVE_DELIMITER);
        String taskCode = delimitedStrings[0];
        if (taskCode.equals("A")) { // Normal task
            return new Task(delimitedStrings[1], Boolean.parseBoolean(delimitedStrings[2]));
        } else if (taskCode.equals("T")) { // Todo task
            return new TodoTask(delimitedStrings[1], Boolean.parseBoolean(delimitedStrings[2]));
        } else if (taskCode.equals("D")) { // Deadline task
            return new DeadlineTask(delimitedStrings[1], // - name
                    Boolean.parseBoolean(delimitedStrings[2]), // - isDone
                    LocalDate.parse(delimitedStrings[3])); // - deadline
        } else if (taskCode.equals("E")) { // Event task
            return new EventTask(delimitedStrings[1], // - name
                    Boolean.parseBoolean(delimitedStrings[2]), // - isDone
                    LocalDate.parse(delimitedStrings[3]), // - startTime
                    LocalDate.parse(delimitedStrings[4])); // - endTime
        } else {
            return null;
        }
    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to add a new normal task, and inserts said task
     * into the tasks variable.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     * @param ui Helper class to print text.
     */
    public static void handleAddNormalTask(String userInput, TaskList tasks, Ui ui) {
        String taskNameToAdd = userInput.substring(3).strip();
        Task taskToAdd = new Task(taskNameToAdd);
        tasks.add(taskToAdd);
        ui.printWithLines("I've added a new task: " + taskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to add a TodoTask, and inserts said task
     * into the tasks variable.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     * @param ui Helper class to print text.
     */
    public static void handleAddTodoTask(String userInput, TaskList tasks, Ui ui) {
        String todoNameToAdd = userInput.substring(4).strip();
        TodoTask todoTaskToAdd = new TodoTask(todoNameToAdd);
        tasks.add(todoTaskToAdd);
        ui.printWithLines("I've added a new todo task: " + todoTaskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to add a DeadlineTask, and inserts said task
     * into the tasks variable.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     * @param ui Helper class to print text.
     */
    public static void handleAddDeadlineTask(String userInput, TaskList tasks, Ui ui) {
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

    /**
     * Processes the rest of user input once we know that user is
     * attempting to add an EventTask, and inserts said task
     * into the tasks variable.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     * @param ui Helper class to print text.
     */
    public static void handleAddEventTask(String userInput, TaskList tasks, Ui ui) {
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

    /**
     * Processes the rest of user input once we know that user is
     * attempting to mark a task as done, and updates said task.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     * @param ui Helper class to print text.
     */
    public static void handleMarkAsDone(String userInput, TaskList tasks, Ui ui) {
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

    /**
     * Processes the rest of user input once we know that user is
     * attempting to mark a task as not done, and updates said task.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     * @param ui Helper class to print text.
     */
    public static void handleMarkAsUndone(String userInput, TaskList tasks, Ui ui) {
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

    /**
     * Processes the rest of user input once we know that user is
     * attempting to delete a task, and deletes said task.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     * @param ui Helper class to print text.
     */
    public static void handleDeleteTask(String userInput, TaskList tasks, Ui ui) {
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

}
