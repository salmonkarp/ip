package taskmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * List-like class to contain tasks, using the
 * implementation of ArrayList.
 */
public class TaskList {

    protected static final int INITIAL_TASK_LIST_CAPACITY = 100;

    private final List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>(INITIAL_TASK_LIST_CAPACITY);
    }

    public TaskList(int initialCapacity) {
        tasks = new ArrayList<>(initialCapacity);
    }

    /**
     * Constructor for a list of tasks
     * @param inputTasks variable number of tasks to be inserted
     */
    public TaskList(Task... inputTasks) {
        tasks = new ArrayList<>(INITIAL_TASK_LIST_CAPACITY);
        Collections.addAll(tasks, inputTasks);
    }

    public String getTasksAsString() {
        StringBuilder result = new StringBuilder();
        for (Task t : tasks) {
            result.append(t.getSaveString()).append('\n');
        }
        return result.toString();
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    public void sort(Comparator<Task> comparator) {
        tasks.sort(comparator);
    }

    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "You have no tasks right now.";
        }
        StringBuilder resultMessage = new StringBuilder("Here are the tasks in your list!\n");
        for (int i = 0; i < tasks.size(); i += 1) {
            resultMessage.append((i + 1)).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return (resultMessage.toString());
    }

}
