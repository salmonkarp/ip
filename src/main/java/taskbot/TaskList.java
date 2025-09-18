package taskbot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * List-like class to contain tasks, using the
 * implementation of ArrayList.
 */
public class TaskList {

    private final List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(int initialCapacity) {
        tasks = new ArrayList<>(initialCapacity);
    }

    /**
     * Constructor for a list of tasks
     * @param inputTasks variable number of tasks to be inserted
     */
    public TaskList(Task... inputTasks) {
        tasks = new ArrayList<>();
        Collections.addAll(tasks, inputTasks);
    }

    protected String getTasksAsString() {
        StringBuilder result = new StringBuilder();
        for (Task t : tasks) {
            result.append(t.getSaveString()).append('\n');
        }
        return result.toString();
    }

    protected void add(Task t) {
        tasks.add(t);
    }

    protected Task get(int index) {
        return tasks.get(index);
    }

    protected Task remove(int index) {
        return tasks.remove(index);
    }

    protected int size() {
        return tasks.size();
    }

    protected void sort(Comparator<Task> comparator) {
        tasks.sort(comparator);
    }

    protected boolean isEmpty() {
        return tasks.isEmpty();
    }
    @Override
    public String toString() {
        StringBuilder resultMessage = new StringBuilder();
        for (int i = 0; i < tasks.size(); i += 1) {
            resultMessage.append((i + 1)).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return (resultMessage.toString());
    }

    protected void clear() {
        tasks.clear();
    }
}
