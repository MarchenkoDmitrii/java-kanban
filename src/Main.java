import managers.*;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();
        manager.createTask(new Task("Купить молоко", "Сходить в ближайший магазин и взять молоко", StatusTask.NEW));
        manager.createTask(new Task("Сходить", "Сходи", StatusTask.DONE));
        manager.createEpic(new Epic("Переезд", "Переезд в другую квартиру", StatusTask.NEW));
        manager.createEpic(new Epic("Ремонт", "Ремонт в новой квартире", StatusTask.NEW));
        manager.createSubTasks(3,new SubTask("Собрать коробки","Коробки для перезда не собраны",StatusTask.DONE));
        manager.createSubTasks(3,new SubTask("Упаковать кошку", "И чтобы она осталась жива!", StatusTask.DONE));
        manager.createSubTasks(3,new SubTask("Сказать слова прощания", "Молитву", StatusTask.DONE));
        System.out.println(manager.getTaskById(1));
        System.out.println(manager.getTaskById(2));
        System.out.println(manager.getTaskById(1));
        System.out.println(manager.getEpicById(4));
        System.out.println(manager.getEpicById(3));
        System.out.println(manager.getSubTaskById(5));
        System.out.println(manager.getSubTaskById(6));
        System.out.println(manager.getSubTaskById(7));
        System.out.println(manager.getSubTaskById(5));
        System.out.println(historyManager.getHistory());
        manager.removeEpicById(3);
        System.out.println(historyManager.getHistory());
    }
}