package taskbot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

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
        LocalDateTime now = LocalDateTime.now();
        TodoTask task = new TodoTask("task", false, now);
        assertEquals("T`task`false`" + now, task.getSaveString());
        task.markDone();
        assertEquals("T`task`true`" + now, task.getSaveString());
    }
}
