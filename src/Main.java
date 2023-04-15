import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        manager.createTask(new Task("Купить молоко", "Сходить в ближайший магазин и взять молоко", StatusTask.NEW));
        manager.createTask(new Task("Сходить", "Сходи", StatusTask.DONE));
        manager.createTask(new Task("Купить", "Купи", StatusTask.DONE));
        manager.createEpic(new Epic("Переезд", "Переезд в другую квартиру", StatusTask.NEW));
        manager.createEpic(new Epic("Ремонт", "Ремонт в новой квартире", StatusTask.NEW));
        manager.createSubTasks(2,new SubTask("Собрать коробки","Коробки для перезда не собраны",StatusTask.DONE));
        manager.createTask(new Task("Сходить на фитнес", "Сходи в зал, зал сам в себя не походит!", StatusTask.DONE));
        manager.createSubTasks(2,new SubTask("Упаковать кошку", "И чтобы она осталась жива!", StatusTask.DONE));
        manager.createSubTasks(2,new SubTask("Сказать слова прощания", "Молитву", StatusTask.DONE));


        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllSubTask());
        System.out.println(manager.getAllEpic());
        System.out.println("Вывели все подзадачи");
        manager.updateSubTasks(new SubTask("Успокоить кота", "Ну кому-то надо это делать", StatusTask.NEW), 4);
        manager.updateEpic(new Epic("Отпуск", "Уехать далеко и надолго", StatusTask.NEW), 3);
        System.out.println(manager.getAllSubTasksByEpic(4));
        System.out.println(manager.getEpicById(4));
        System.out.println("Вывели эпик 2 и все его сабтаски");

        System.out.println(manager.getEpicById(4));
        System.out.println(manager.getAllSubTasksByEpic(4));
        System.out.println("Вывели эпик 2 и все его сабтаски");
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubTask());


        System.out.println(manager.getEpicById(4));
        System.out.println(manager.getSubTaskById(8));
        System.out.println(manager.getTaskById(1));
        System.out.println(manager.getTaskById(2));
        System.out.println(manager.getTaskById(3));

        manager.updateTask(new Task("Сходить в кино","Найти кинотеатр и нормальный фильм",StatusTask.NEW),5);
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllSubTask());
        System.out.println(manager.getAllEpic());
        System.out.println(Managers.getDefaultHistory().getHistory());

    }
}