package test;

import managers.FileBackedTasksManager;
import managers.ManagerSaveException;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import java.io.File;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FileBackedTasksManagerIOTest {
    @Test
    void saveLoadEmptyTest() throws ManagerSaveException {
        FileBackedTasksManager manager = new FileBackedTasksManager();
        manager.save();
        File file1 = new File("C:\\Users\\Angelina\\dev\\java-kanban\\src\\Test\\resources\\Task.csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        fileBackedTasksManager.loadFromFile(file1);
        assertEquals(fileBackedTasksManager,manager);
    }
    @Test
    void saveLoadEmptyEpicTest() throws ManagerSaveException {
        FileBackedTasksManager manager = new FileBackedTasksManager();
        manager.createTask(new Task("Купить молоко", "Сходить в ближайший магазин и взять молоко",
                StatusTask.NEW,11L,"15.06.1997 16:41"));
        manager.createTask(new Task("Заплатить за квартиру", "Кварплата",
                StatusTask.DONE,60L, "14.06.1997 15:41"));
        manager.createEpic(new Epic("Переезд", "Переезд в другую квартиру"));
        manager.createEpic(new Epic("Ремонт", "Ремонт в новой квартире"));
        manager.createSubTasks(new SubTask("Собрать коробки","Коробки для переeзда не собраны",
                StatusTask.DONE,11L, "17.06.1997 15:55",3));
        manager.createSubTasks(new SubTask("Упаковать вещи", "Вещи в коробку!",
                StatusTask.NEW,11L, "19.06.1997 15:41",3));
        manager.createSubTasks(new SubTask("Сказать слова прощания", "Молитву",
                StatusTask.DONE,11L, "18.06.1997 15:41",3));
        manager.save();
        File file1 = new File("C:\\Users\\Angelina\\dev\\java-kanban\\src\\Test\\resources\\Task.csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        fileBackedTasksManager.loadFromFile(file1);
        assertEquals(fileBackedTasksManager,manager);
    }

}