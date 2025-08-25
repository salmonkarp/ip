import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private List<Task> tasks;

    public TaskList(int intialCapacity) {
        tasks = new ArrayList<Task>(intialCapacity);
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
