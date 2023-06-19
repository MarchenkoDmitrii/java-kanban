package test;

import managers.InMemoryTaskManager;
import managers.ManagerSaveException;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void testStatusEmptySubTasks() throws ManagerSaveException {
        Epic epic = new Epic("test","test");
        assertEquals(epic.getStatus(),StatusTask.NEW);
    }

    @Test
    void testStatusAllSubTasksNew() throws ManagerSaveException {
        InMemoryTaskManager file = new InMemoryTaskManager();
        Epic epic = new Epic("test","test");
        file.createEpic(epic);
        file.createSubTasks(new SubTask("test","test",StatusTask.NEW, epic.getId()));
        file.createSubTasks(new SubTask("test","test",StatusTask.NEW, epic.getId()));
        file.createSubTasks(new SubTask("test","test",StatusTask.NEW, epic.getId()));
        file.updateEpicStatus(epic.getId());
        assertEquals(epic.getStatus(), StatusTask.NEW);
    }

    @Test
    void testStatusAllSubTasksDone() throws ManagerSaveException {
        InMemoryTaskManager file = new InMemoryTaskManager();
        Epic epic = new Epic("test","test");
        file.createEpic(epic);
        file.createSubTasks(new SubTask("test","test",StatusTask.DONE, epic.getId()));
        file.createSubTasks(new SubTask("test","test",StatusTask.DONE, epic.getId()));
        file.createSubTasks(new SubTask("test","test",StatusTask.DONE, epic.getId()));
        file.updateEpicStatus(epic.getId());
        assertEquals(epic.getStatus() ,StatusTask.DONE);
    }

    @Test
    void testStatusMixedSubTasks() throws ManagerSaveException {
        InMemoryTaskManager file = new InMemoryTaskManager();
        Epic epic = new Epic("test","test");
        file.createEpic(epic);
        file.createSubTasks(new SubTask("test","test",StatusTask.NEW, epic.getId()));
        file.createSubTasks(new SubTask("test","test",StatusTask.DONE, epic.getId()));
        file.createSubTasks(new SubTask("test","test",StatusTask.IN_PROGRESS, epic.getId()));
        file.updateEpicStatus(epic.getId());
        assertEquals(epic.getStatus(),StatusTask.IN_PROGRESS);
    }

    @Test
    void testStatusInProgressSubTasks() throws ManagerSaveException {
        InMemoryTaskManager file = new InMemoryTaskManager();
        Epic epic =new Epic("test","test");
        file.createEpic(epic);
        file.createSubTasks(new SubTask("test","test",StatusTask.IN_PROGRESS, epic.getId()));
        file.createSubTasks(new SubTask("test","test",StatusTask.IN_PROGRESS, epic.getId()));
        file.createSubTasks(new SubTask("test","test",StatusTask.IN_PROGRESS, epic.getId()));
        file.updateEpicStatus(epic.getId());
        assertEquals(epic.getStatus(),StatusTask.IN_PROGRESS);
    }

}