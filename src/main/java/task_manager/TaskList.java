package task_manager;

import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private static int INITIAL_TASK_LIST_CAPACITY = 100;

    private List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<Task>(INITIAL_TASK_LIST_CAPACITY);
    }

    public TaskList(int initialCapacity) {
        tasks = new ArrayList<Task>(initialCapacity);
    }

    public String getTasksAsString() {
        String result = "";
        for (Task t : tasks) {
            result += t.getSaveString() + '\n';
        }
        return result;
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
}
