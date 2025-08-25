import java.time.LocalDate;

public class Parser {

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

    public static Task getTaskFromSaveString(String s) {
        String[] delimitedStrings = s.split(">");
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
}
