package test;

import kvserver.KVServer;
import managers.HttpTaskManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest {
    @BeforeAll
    static void Start() throws IOException {
        new KVServer().start();
    }

    //Этот тест проверяет возможность загрузки и сохранения пустых файлов
    @Test
    void saveLoadEmptyTest() throws IOException, InterruptedException, URISyntaxException {

        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078/");
        manager.save();
        assertTrue(manager.getAllTasks().isEmpty());
        assertTrue(manager.getAllEpic().isEmpty());
        assertTrue(manager.getAllSubTask().isEmpty());
        HttpTaskManager manager1 = new HttpTaskManager("http://localhost:8078/");
        manager1.load();
        assertTrue(manager1.getAllTasks().isEmpty());
        assertTrue(manager1.getAllEpic().isEmpty());
        assertTrue(manager1.getAllSubTask().isEmpty());
    }

    @Test
    void saveLoadTest() throws IOException, InterruptedException, URISyntaxException {
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078/");
        manager.createTask(new Task("sda", "dasd"));
        manager.createTask(new Task("sda", "dasd"));
        manager.createEpic(new Epic("ad", "ad"));
        manager.createSubTasks(new SubTask("dasd", "dasd", StatusTask.NEW,3));
        HttpTaskManager manager1 = new HttpTaskManager("http://localhost:8078/");
        manager1.load();
        assertEquals(manager1,manager);
    }
}