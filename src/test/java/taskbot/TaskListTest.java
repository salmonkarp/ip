package taskbot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void initTest() {
        TaskList taskList = new TaskList();
        taskList.add(new Task("test1"));
        taskList.add(new TodoTask("test2"));
        taskList.add(new DeadlineTask("test3", LocalDate.parse("2024-10-20")));
        taskList.add(new EventTask("test4", LocalDate.parse("2025-10-11"), LocalDate.parse("2026-10-11")));
        assertEquals("[ ] test1", taskList.get(0).toString());
        assertEquals("[T][ ] test2", taskList.get(1).toString());
        assertEquals("[D][ ] test3 (by: Oct 20 2024)", taskList.get(2).toString());
        assertEquals("[E][ ] test4 (from: Oct 11 2025 to: Oct 11 2026)", taskList.get(3).toString());
    }

    @Test
    public void removeTest() {
        TaskList taskList = new TaskList(
                new Task("test1"),
                new TodoTask("test2"),
                new DeadlineTask("test3", LocalDate.parse("2024-10-20")),
                new EventTask("test4", LocalDate.parse("2025-10-11"), LocalDate.parse("2026-10-11")));

        taskList.remove(1);
        assertEquals("[ ] test1", taskList.get(0).toString());
        assertEquals("[D][ ] test3 (by: Oct 20 2024)", taskList.get(1).toString());
        assertEquals("[E][ ] test4 (from: Oct 11 2025 to: Oct 11 2026)", taskList.get(2).toString());
    }

    @Test
    public void markTest() {
        TaskList taskList = new TaskList(
                new Task("test1"),
                new TodoTask("test2"),
                new DeadlineTask("test3", LocalDate.parse("2024-10-20")),
                new EventTask("test4", LocalDate.parse("2025-10-11"), LocalDate.parse("2026-10-11")));

        taskList.get(1).markDone();
        taskList.get(3).markDone();
        assertEquals("[ ] test1", taskList.get(0).toString());
        assertEquals("[T][X] test2", taskList.get(1).toString());
        assertEquals("[D][ ] test3 (by: Oct 20 2024)", taskList.get(2).toString());
        assertEquals("[E][X] test4 (from: Oct 11 2025 to: Oct 11 2026)", taskList.get(3).toString());
        taskList.get(1).markUndone();
        assertEquals("[T][ ] test2", taskList.get(1).toString());
    }
}
