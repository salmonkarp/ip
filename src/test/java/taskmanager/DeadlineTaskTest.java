package taskmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTaskTest {
    @Test
    public void initTest() {
        assertEquals("[D][ ] test1 (by: Oct 22 1992)",
                new DeadlineTask("test1", LocalDate.parse("1992-10-22")).toString());
        assertEquals("[D][ ] randomWordsAndLetters (by: Jan 30 2024)",
                new DeadlineTask("randomWordsAndLetters", LocalDate.parse("2024-01-30")).toString());
    }

    @Test
    public void saveStringTest() {
        DeadlineTask task = new DeadlineTask("task", LocalDate.parse("1992-10-22"));
        assertEquals("D`task`false`1992-10-22", task.getSaveString());
        task.markDone();
        assertEquals("D`task`true`1992-10-22", task.getSaveString());
    }
}
