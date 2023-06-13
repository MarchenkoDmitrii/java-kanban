package Test;

import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.StatusTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    private HistoryManager manager;

    @BeforeEach
    public void setUp() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    void add() {
        final List<Task> history = manager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    public void testGetHistory() {
        HistoryManager manager1 = new InMemoryHistoryManager();
        List<Task> history = manager1.getHistory();
        Assert.assertTrue("История должна быть пустой", history.isEmpty());
    }
    @Test
    public void testAddDuplicate() {
        Task task = new Task("test","test");
        HistoryManager manager2 = new InMemoryHistoryManager();
        manager2.add(task);
        manager2.add(task);
        List<Task> history = manager.getHistory();
        Assert.assertEquals("История должна содержать одну задачу", 1, history.size());
        Assert.assertEquals("Добавлена такая же запись", task, history.get(0));
    }
    @Test
    public void testRemoveFromBeginning() {
        Task task1 = new Task("test1","test1");
        Task task2 = new Task("test2","test2");
        manager.add(task1);
        manager.add(task2);
        manager.remove(task1.getId());
        List<Task> history = manager.getHistory();
        Assert.assertEquals("История должна содержать одну задачу", 1, history.size());
        Assert.assertEquals("Первая задача должна быть такой же", task2.getName(), history.get(0).getName());
    }

    @Test
    public void testRemoveFromMiddle() {
        Task task1 = new Task("test","test");
        Task task2 = new Task("test","test");
        Task task3 = new Task("test","test");
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task2.getId());
        List<Task> history = manager.getHistory();
        Assert.assertEquals("История должна содержать две задачи", 2, history.size());
        Assert.assertEquals("Первая задача должна быть такой же", task1, history.get(0));
        Assert.assertEquals("Вторая задача должна быть такой же", task3, history.get(1));
    }

    @Test
    public void testRemoveFromEnd() {
        Task task1 = new Task("test","test");
        Task task2 = new Task("test","test");
        manager.add(task1);
        manager.add(task2);
        manager.remove(task2.getId());
        List<Task> history = manager.getHistory();
        Assert.assertEquals("История должна содержать одну задачу", 1, history.size());
        Assert.assertEquals("Первая задача должна быть такой же", task1, history.get(0));
    }
}
