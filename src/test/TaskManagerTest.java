package test;


import managers.ManagerSaveException;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    public T manager;

    @Test
    public void testGetAllTasks() throws ManagerSaveException {
        Task task = new Task("task","task");
        manager.createTask(task);
        // Test with standard behavior
        assertNotNull(manager.getAllTasks());

        // Test with empty list of tasks
        manager.removeAllTasks();
        assertTrue(manager.getAllTasks().isEmpty());

        // Test with invalid task identifier
        assertNull(manager.getTaskById(0));
    }

    @Test
    public void testGetAllEpic() throws ManagerSaveException {
        // Test with standard behavior
        assertNotNull(manager.getAllEpic());

        // Test with empty list of epics
        manager.removeAllEpics();
        assertTrue(manager.getAllEpic().isEmpty());

        // Test with invalid epic identifier
        assertNull(manager.getEpicById(0));
    }

    @Test
    public void testGetAllSubTask() throws ManagerSaveException {
        // Test with standard behavior
        assertNotNull(manager.getAllSubTask());

        // Test with empty list of subtasks
        manager.removeAllSubTasks();
        assertTrue(manager.getAllSubTask().isEmpty());

        // Test with invalid subtask identifier
        assertNull(manager.getSubTaskById(0));
    }

    @Test
    public void testCreateTask() throws ManagerSaveException {
        Task task = new Task("test","test");
        manager.createTask(task);
        assertNotNull(manager.getTaskById(task.getId()));
    }

    @Test
    public void testUpdateTask() throws ManagerSaveException {
        Task task = new Task("test","test");
        manager.createTask(task);
        task.setName("Updated task name");
        manager.updateTask(task, task.getId());
        assertEquals("Updated task name", manager.getTaskById(task.getId()).getName());
    }

    @Test
    public void testCreateEpic() throws ManagerSaveException {
        Epic epic = new Epic("test","test");
        manager.createEpic(epic);
        assertNotNull(manager.getEpicById(epic.getId()));
    }

    @Test
    public void testUpdateEpic() throws ManagerSaveException {
        Epic epic = new Epic("test","test");
        manager.createEpic(epic);
        epic.setName("Updated epic name");
        manager.updateEpic(epic, epic.getId());
        assertEquals("Updated epic name", manager.getEpicById(epic.getId()).getName());
    }

    @Test
    public void testCreateSubTasks() throws ManagerSaveException {
        Epic epic = new Epic("test","test");
        manager.createEpic(epic);
        SubTask subTask = new SubTask("test","test", StatusTask.NEW, epic.getId());
        manager.createSubTasks(subTask);
        assertNotNull(manager.getSubTaskById(subTask.getId()));
    }

    @Test
    public void testUpdateSubTasks() throws ManagerSaveException {
        Epic epic = new Epic("test","test");
        manager.createEpic(epic);
        SubTask subTask = new SubTask("test","test", StatusTask.NEW, epic.getId());
        manager.createSubTasks(subTask);
        subTask.setName("Updated subtask name");
        manager.updateSubTasks(subTask, subTask.getId());
        assertEquals("Updated subtask name", manager.getSubTaskById(subTask.getId()).getName());
    }

    @Test
    public void testRemoveTaskById() throws ManagerSaveException {
        Task task = new Task("test","test");
        manager.createTask(task);
        manager.removeTaskById(task.getId());
        assertNull(manager.getTaskById(task.getId()));
    }

    @Test
    public void testRemoveEpicById() throws ManagerSaveException {
        Epic epic = new Epic("test","test");
        manager.createEpic(epic);
        manager.removeEpicById(epic.getId());
        assertNull(manager.getEpicById(epic.getId()));
    }

    @Test
    public void testRemoveSubTaskById() throws ManagerSaveException {
        Epic epic = new Epic("test","test");
        manager.createEpic(epic);
        SubTask subTask = new SubTask("test","test",StatusTask.NEW, epic.getId());
        manager.createSubTasks(subTask);
        manager.removeSubTaskById(subTask.getId());
        assertNull(manager.getSubTaskById(subTask.getId()));
    }

    @Test
    public void testGetAllSubTasksByEpic() throws ManagerSaveException {
        Epic epic = new Epic("test","test");
        manager.createEpic(epic);
        SubTask subTask = new SubTask("test","test",StatusTask.NEW, epic.getId());
        manager.createSubTasks(subTask);
        assertNotNull(manager.getAllSubTasksByEpic(epic.getId()));
    }

    @Test
    public void testUpdateEpicStatus() throws ManagerSaveException {
        Epic epic = new Epic("test","test");
        manager.createEpic(epic);
        SubTask subTask = new SubTask("test","test",StatusTask.NEW, epic.getId());
        subTask.setStatus(StatusTask.IN_PROGRESS);
        manager.createSubTasks(subTask);
        manager.updateEpicStatus(epic.getId());
        assertEquals(StatusTask.IN_PROGRESS, manager.getEpicById(epic.getId()).getStatus());
    }

}


