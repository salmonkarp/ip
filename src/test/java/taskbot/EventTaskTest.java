package taskbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static taskbot.Utility.parseTime;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class EventTaskTest {
    @Test
    public void initTest() {
        assertEquals("[E][ ] test1 (from: Oct 22 1992 to: Jul 30 1995)",
                new EventTask("test1", parseTime("1992-10-22"),
                        parseTime("1995-07-30")).toString());
        assertEquals("[E][ ] randomWordsAndLetters (from: Jan 30 2024 to: Jan 31 2025)",
                new EventTask("randomWordsAndLetters", parseTime("2024-01-30"),
                        parseTime("2025-01-31")).toString());
    }

    @Test
    public void saveStringTest() {
        LocalDateTime now = LocalDateTime.now();
        EventTask task = new EventTask("task", false, now, parseTime("1992-10-22"),
                parseTime("1995-10-31"));
        assertEquals("E`task`false`" + now + "`1992-10-22 00:00`1995-10-31 00:00", task.getSaveString());
        task.markDone();
        assertEquals("E`task`true`" + now + "`1992-10-22 00:00`1995-10-31 00:00", task.getSaveString());
    }
}
