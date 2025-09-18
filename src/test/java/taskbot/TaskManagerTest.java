package taskbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class TaskManagerTest {
    @Test
    public void initTest() {
        TaskManager taskManager = new TaskManager();
        taskManager.clearData();
        assertNotEquals(null, taskManager);
    }

    @Test
    public void addTodoCommandTest() {
        TaskManager taskManager = new TaskManager();
        taskManager.clearData();
        String response = taskManager.getResponse("todo read book");
        assertEquals("I've added a new todo task: [T][ ] read book\nYou have 1 task(s) now.", response);

        response = taskManager.getResponse("list");
        assertEquals("""
                Here are the tasks in your list!
                1. [T][ ] read book
                """, response);
    }

    @Test
    public void addDeadlineCommandTest() {
        TaskManager taskManager = new TaskManager();
        taskManager.clearData();
        String response = taskManager.getResponse("deadline submit report / 2024-12-01 23:59");
        assertEquals("I've added a new deadline task: [D][ ] submit report (by: Dec 1 2024 23:59)\n"
                + "You have 1 task(s) now.", response);

        response = taskManager.getResponse("list");
        assertEquals("""
                Here are the tasks in your list!
                1. [D][ ] submit report (by: Dec 1 2024 23:59)
                """, response);
    }

    @Test
    public void addEventCommandTest() {
        TaskManager taskManager = new TaskManager();
        taskManager.clearData();
        String response = taskManager.getResponse(
                "event project meeting / 2024-11-15 14:00 / 2024-11-15 15:00");
        assertEquals("""
                I've added a new event task: [E][ ] project meeting (from: Nov 15 2024 14:00 to: Nov 15 2024 15:00)
                You have 1 task(s) now.""", response);

        response = taskManager.getResponse("list");
        assertEquals("""
                Here are the tasks in your list!
                1. [E][ ] project meeting (from: Nov 15 2024 14:00 to: Nov 15 2024 15:00)
                """, response);
    }

    @Test
    public void markTaskAsDoneTest() {
        TaskManager taskManager = new TaskManager();
        taskManager.clearData();
        taskManager.getResponse("todo read book");
        String response = taskManager.getResponse("mark 1");
        assertEquals("Nice! I've marked this task as done:\n"
                + "[T][X] read book", response);

        response = taskManager.getResponse("list");
        assertEquals("""
                Here are the tasks in your list!
                1. [T][X] read book
                """, response);
    }

    @Test
    public void markTaskAsUndoneTest() {
        TaskManager taskManager = new TaskManager();
        taskManager.clearData();
        taskManager.getResponse("todo read book");
        taskManager.getResponse("mark 1");
        String response = taskManager.getResponse("unmark 1");
        assertEquals("Nice! I've marked this task as not done:\n"
                + "[T][ ] read book", response);

        response = taskManager.getResponse("list");
        assertEquals("""
                Here are the tasks in your list!
                1. [T][ ] read book
                """, response);
    }

    @Test
    public void deleteTaskTest() {
        TaskManager taskManager = new TaskManager();
        taskManager.clearData();
        taskManager.getResponse("todo read book");
        String response = taskManager.getResponse("delete 1");
        assertEquals("""
                I've removed this task:\s
                [T][ ] read book
                You have 0 task(s) now.""", response);

        response = taskManager.getResponse("list");
        assertEquals("You have no tasks right now.", response);
    }

    @Test
    public void findCommandTest() {
        TaskManager taskManager = new TaskManager();
        taskManager.clearData();
        taskManager.getResponse("todo read book");
        taskManager.getResponse("deadline submit report / 2024-12-01 23:59");
        taskManager.getResponse("event project meeting / 2024-11-15 14:00 / 2024-11-15 15:00");
        String response = taskManager.getResponse("find report");
        assertEquals("""
                Here are the matching tasks in your list:
                1. [D][ ] submit report (by: Dec 1 2024 23:59)
                """, response);
    }

    @Test
    public void sortCommandTest() {
        TaskManager taskManager = new TaskManager();
        taskManager.clearData();
        taskManager.getResponse("todo read book");
        taskManager.getResponse("deadline submit report / 2024-12-01 23:59");
        taskManager.getResponse("event project meeting / 2024-11-15 14:00 / 2024-11-15 15:00");
        String response = taskManager.getResponse("sort");
        assertEquals("""
                I've sorted your tasks.
                1. [E][ ] project meeting (from: Nov 15 2024 14:00 to: Nov 15 2024 15:00)
                2. [T][ ] read book
                3. [D][ ] submit report (by: Dec 1 2024 23:59)
                """, response);
    }
}
