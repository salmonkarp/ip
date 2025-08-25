package taskmanager;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTaskTest {
    @Test
    public void initTest() {
        assertEquals("[E][ ] test1 (from: Oct 22 1992 to: Jul 30 1995)",
                new EventTask("test1", LocalDate.parse("1992-10-22"),
                        LocalDate.parse("1995-07-30")).toString());
        assertEquals("[E][ ] randomWordsAndLetters (from: Jan 30 2024 to: Jan 31 2025)",
                new EventTask("randomWordsAndLetters", LocalDate.parse("2024-01-30"),
                        LocalDate.parse("2025-01-31")).toString());
    }

    @Test
    public void saveStringTest() {
        EventTask task = new EventTask("task", LocalDate.parse("1992-10-22"),
                LocalDate.parse("1995-10-31"));
        assertEquals("E`task`false`1992-10-22`1995-10-31", task.getSaveString());
        task.markDone();
        assertEquals("E`task`true`1992-10-22`1995-10-31", task.getSaveString());
    }
}
