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
        SAVE,
        BYE,
        FIND,
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
            } else if (input.equals("save")) {
                return TaskCommand.SAVE;
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
            } else if (input.startsWith("find ")) {
                return TaskCommand.FIND;
            } else {
                return TaskCommand.UNKNOWN;
            }
        }

    }

    protected static final String SAVE_DELIMITER = "`";

    public static Task getTaskFromSaveString(String s) {
        String[] delimitedStrings = s.split(SAVE_DELIMITER);
        String taskCode = delimitedStrings[0];
        return switch (taskCode) {
        case "A" -> // Normal task
            new Task(delimitedStrings[1], Boolean.parseBoolean(delimitedStrings[2]));
        case "T" -> // TodoTask
            new TodoTask(delimitedStrings[1], Boolean.parseBoolean(delimitedStrings[2]));
        case "D" -> // Deadline task
            new DeadlineTask(delimitedStrings[1], // - name
                    Boolean.parseBoolean(delimitedStrings[2]), // - isDone
                    LocalDate.parse(delimitedStrings[3])); // - deadline
        case "E" -> // Event task
            new EventTask(delimitedStrings[1], // - name
                    Boolean.parseBoolean(delimitedStrings[2]), // - isDone
                    LocalDate.parse(delimitedStrings[3]), // - startTime
                    LocalDate.parse(delimitedStrings[4])); // - endTime
        default -> null;
        };
    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to add a new normal task, and inserts said task
     * into the tasks variable.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     */
    public static String handleAddNormalTask(String userInput, TaskList tasks) {
        String taskNameToAdd = userInput.substring(3).strip();
        Task taskToAdd = new Task(taskNameToAdd);
        tasks.add(taskToAdd);
        return ("I've added a new task: " + taskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to add a TodoTask, and inserts said task
     * into the tasks variable.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     */
    public static String handleAddTodoTask(String userInput, TaskList tasks) {
        String todoNameToAdd = userInput.substring(4).strip();
        TodoTask todoTaskToAdd = new TodoTask(todoNameToAdd);
        tasks.add(todoTaskToAdd);
        return ("I've added a new todo task: " + todoTaskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to add a DeadlineTask, and inserts said task
     * into the tasks variable.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     */
    public static String handleAddDeadlineTask(String userInput, TaskList tasks) {
        String[] deadlineTaskDetails = userInput.substring(8).split("/");
        if (deadlineTaskDetails.length != 2) {
            return "Wrong format! Type 'deadline [name] / [deadline]'";
        }
        try {
            LocalDate.parse(deadlineTaskDetails[1].strip());
        } catch (Exception e) {
            return "Wrong format! Type 'deadline [name] / [deadline]'";
        }
        String deadlineNameToAdd = deadlineTaskDetails[0].strip();
        String deadlineTime = deadlineTaskDetails[1].strip();
        DeadlineTask deadlineTaskToAdd = new DeadlineTask(deadlineNameToAdd, LocalDate.parse(deadlineTime));
        tasks.add(deadlineTaskToAdd);
        return ("I've added a new deadline task: " + deadlineTaskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to add an EventTask, and inserts said task
     * into the tasks variable.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     */
    public static String handleAddEventTask(String userInput, TaskList tasks) {
        String[] eventTaskDetails = userInput.substring(5).split("/");
        if (eventTaskDetails.length != 3) {
            return ("Wrong format! Type 'event [name] / [startTime] / [endTime]'");
        }
        try {
            LocalDate.parse(eventTaskDetails[1].strip());
            LocalDate.parse(eventTaskDetails[2].strip());
        } catch (Exception e) {
            return ("Wrong format! Type 'event [name] / [startTime] / [endTime]'");
        }
        String eventNameToAdd = eventTaskDetails[0].strip();
        String eventStartTime = eventTaskDetails[1].strip();
        String eventEndTime = eventTaskDetails[2].strip();
        EventTask eventTaskToAdd = new EventTask(eventNameToAdd,
                LocalDate.parse(eventStartTime),
                LocalDate.parse(eventEndTime));
        tasks.add(eventTaskToAdd);
        return ("I've added a new event task: " + eventTaskToAdd.toString()
                + "\nYou have " + tasks.size() + " tasks now.");
    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to mark a task as done, and updates said task.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     */
    public static String handleMarkAsDone(String userInput, TaskList tasks) {
        try {
            Integer taskIndexToMark = Integer.parseInt(userInput.substring(4).strip()) - 1;
            if (taskIndexToMark < 0 || taskIndexToMark >= tasks.size()) {
                return ("Index out of range. Are you sure you inputted the right index?");
            } else {
                tasks.get(taskIndexToMark).markDone();
                return ("Nice! I've marked this task as done:\n" + tasks.get(taskIndexToMark));
            }
        } catch (NumberFormatException e) {
            return ("Format error! Did you put a single number after 'mark'?");
        }

    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to mark a task as not done, and updates said task.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     */
    public static String handleMarkAsUndone(String userInput, TaskList tasks) {
        try {
            int taskIndexToMark = Integer.parseInt(userInput.substring(6).strip()) - 1;
            if (taskIndexToMark < 0 || taskIndexToMark >= tasks.size()) {
                return ("Task was not found. Are you sure you inputted the right index?");
            } else {
                tasks.get(taskIndexToMark).markUndone();
                return ("Nice! I've unmarked this task as done:\n" + tasks.get(taskIndexToMark));
            }
        } catch (NumberFormatException e) {
            return ("Format error! Did you put a number after 'unmark'?");
        }

    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to delete a task, and deletes said task.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     */
    public static String handleDeleteTask(String userInput, TaskList tasks) {
        try {
            int taskIndexToDelete = Integer.parseInt(userInput.substring(6).strip()) - 1;
            if (taskIndexToDelete < 0 || taskIndexToDelete >= tasks.size()) {
                return ("Index out of range. Are you sure you inputted the right index?");
            }
            Task deletedTask = tasks.remove(taskIndexToDelete);
            return ("I've removed this task: " + deletedTask.toString()
                    + "\nYou have " + tasks.size() + " tasks now.");
        } catch (NumberFormatException e) {
            return ("Format error! Did you put a single number after 'delete'?");
        }
    }

    /**
     * Processes the rest of user input once we know that user is
     * attempting to find a task through a query string, and displays said tasks.
     * @param userInput Input that user has provided prior.
     * @param tasks List of tasks already present.
     */
    public static String handleFindTask(String userInput, TaskList tasks) {
        String query = userInput.substring(5);
        System.out.println(query);
        StringBuilder result = new StringBuilder("Here are the matching tasks in your list:\n");
        int counter = 1;
        for (int i = 0; i < tasks.size(); i += 1) {
            Task currentTask = tasks.get(i);
            if (currentTask.getName().contains(query)) {
                result.append(counter + ". " + currentTask.toString() + '\n');
                counter += 1;
            }
        }
        return (result.toString());
    }

}
