package taskbot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeadlineTaskTest {
    @Test
    public void initTest() {
        assertEquals("[D][ ] test1 (by: Oct 22 1992)",
                new DeadlineTask("test1", TaskManager.parseTime("1992-10-22")).toString());
        assertEquals("[D][ ] randomWordsAndLetters (by: Jan 30 2024)",
                new DeadlineTask("randomWordsAndLetters", TaskManager.parseTime("2024-01-30")).toString());
    }

    @Test
    public void saveStringTest() {
        LocalDateTime now = LocalDateTime.now();
        DeadlineTask task = new DeadlineTask("task", false, now, TaskManager.parseTime("1992-10-22"));
        assertEquals("D`task`false`" + now + "`1992-10-22 00:00", task.getSaveString());
        task.markDone();
        assertEquals("D`task`true`" + now + "`1992-10-22 00:00", task.getSaveString());
    }
}
