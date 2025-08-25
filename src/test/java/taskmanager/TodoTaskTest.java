package taskmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTaskTest {
    @Test
    public void nameTest() {
        assertEquals("[T][ ] test1", new TodoTask("test1").toString());
        assertEquals("[T][ ] randomWordsAndLetters", new TodoTask("randomWordsAndLetters").toString());
    }

    @Test
    public void isDoneTest() {
        TodoTask doneTask = new TodoTask("isDone");
        doneTask.markDone();
        assertEquals("[T][ ] notDone", new TodoTask("notDone").toString());
        assertEquals("[T][X] isDone", doneTask.toString());
    }

    @Test
    public void saveStringTest() {
        TodoTask task = new TodoTask("task");
        assertEquals("T`task`false", task.getSaveString());
        task.markDone();
        assertEquals("T`task`true", task.getSaveString());
    }
}
