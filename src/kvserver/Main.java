package kvserver;

import managers.HttpTaskManager;
import managers.Managers;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        new KVServer().start();
        Managers.getFile().createTask(new Task("sda","dasd"));
        Managers.getFile().createTask(new Task("sda","dasd"));
        Managers.getFile().createEpic(new Epic("ad","ad"));
        Managers.getFile().createSubTasks(new SubTask("dasd","dasd", StatusTask.NEW,3));
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078/");
        manager.load();
        System.out.println(manager.getAllTasks());
    }

}
