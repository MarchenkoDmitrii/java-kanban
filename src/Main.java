public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.createTask(new Task("Купить молоко", "Сходить в ближайший магазин и взять молоко", "NEW"));
        manager.createEpic(new Epic("Переезд", "Переезд в другую квартиру", "NEW"));
        manager.createEpic(new Epic("Ремонт", "Ремонт в новой квартире", "NEW"));
        manager.createSubTasks(2,new SubTask("Собрать коробки","Коробки для перезда не собраны","DONE"));
        manager.createTask(new Task("Сходить на фитнес", "Сходи в зал, зал сам в себя не походит!", "DONE"));
        manager.createSubTasks(2,new SubTask("Упаковать кошку", "И чтобы она осталась жива!", "DONE"));
        manager.createSubTasks(2,new SubTask("Сказать слова прощания", "Молитву", "DONE"));

        System.out.println(manager.getAllTasks());
       System.out.println(manager.getAllSubTask());
        System.out.println(manager.getAllEpic());
        System.out.println("Вывели все подзадачи");
        manager.updateSubTasks(new SubTask("Успокоить кота", "Ну кому-то надо это делать", "NEW"), 4);
        manager.updateEpic(new Epic("Отпуск", "Уехать далеко и надолго", "NEW"), 3);
        System.out.println(manager.getAllSubTasksByEpic(2));
        System.out.println(manager.getEpicById(2));
        System.out.println("Вывели эпик 2 и все его сабтаски");
        manager.removeSubTaskById(4);
        manager.removeAllSubTasks();
        System.out.println(manager.getEpicById(2));
        System.out.println(manager.getAllSubTasksByEpic(2));
        System.out.println("Вывели эпик 2 и все его сабтаски");
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubTask());


        System.out.println(manager.getEpicById(2));
        System.out.println(manager.getSubTaskById(4));
        System.out.println(manager.getTaskById(1));

        manager.removeEpicById(3);
        manager.updateTask(new Task("Сходить в кино","Найти кинотеатр и нормальный фильм","NEW"),5);
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllSubTask());
        System.out.println(manager.getAllEpic());

    }
}