package managers;

import tasks.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    @Override
    public void removeAllTasks() throws ManagerSaveException {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubTasks() throws ManagerSaveException {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public void removeAllEpics() throws ManagerSaveException {
        super.removeAllEpics();
        save();
    }

    @Override
    public void createTask(Task task) throws ManagerSaveException {
        super.createTask(task);
        save();
    }

    @Override
    public void updateTask(Task task, int id) throws ManagerSaveException {
        super.updateTask(task, id);
        save();
    }

    @Override
    public void createEpic(Epic epic) throws ManagerSaveException {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateEpic(Epic epic, int id) throws ManagerSaveException {
        super.updateEpic(epic, id);
        save();
    }

    @Override
    public void createSubTasks(int epicID, SubTask subTask) throws ManagerSaveException {
        super.createSubTasks(epicID, subTask);
        save();
    }

    @Override
    public void updateSubTasks(SubTask subTask, int id) throws ManagerSaveException {
        super.updateSubTasks(subTask, id);
        save();
    }

    @Override
    public void removeTaskById(int id) throws ManagerSaveException {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) throws ManagerSaveException {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeSubTaskById(int id) throws ManagerSaveException {
        super.removeSubTaskById(id);
        save();
    }

    @Override
    public void updateEpicStatus(int id) throws ManagerSaveException {
        super.updateEpicStatus(id);
        save();
    }

    @Override
    public Task getTaskById(int id) throws ManagerSaveException {
       Task history = super.getTaskById(id);
       save();
       return history;
    }

    @Override
    public SubTask getSubTaskById(int id) throws ManagerSaveException {
        SubTask history = super.getSubTaskById(id);
        save();
        return history;
    }

    @Override
    public Epic getEpicById(int id) throws ManagerSaveException {
        Epic history = super.getEpicById(id);
        save();
        return history;
    }

    // Метод преобразования задачи в строку
    public static String toString(Task task) {

        String out;

    // Т.к. у в файле у нас имеется две величины строк, то разобьем их условием чтобы не уйти в аутофстек
        if (task.getTypeTask().equals(TypeTask.SubTask)){
            out = String.format("%d,%s,%s,%S,%s,%d", task.getId(),task.getTypeTask(), task.getName(),
                   task.getStatus(), task.getDescription(),SubTask.getEpicsID());
        }else {
            out = String.format("%d,%s,%s,%S,%s", task.getId(), task.getTypeTask(), task.getName(),
                    task.getStatus(), task.getDescription());
        }
        return out;
    }

    // Метод преобразования строки в задачу
    public static Task fromString(String value) {

        String[] stringsTask = value.split(",");
        Task task;

        // Точно такое же условие как и в методе выше
        if (!TypeTask.valueOf(stringsTask[1]).equals(TypeTask.SubTask)) {

            // Для Task были созданы новые конструкторы, которые отвечают только за запись/чтение из файла
            task = new Task(Integer.parseInt(stringsTask[0]), stringsTask[2], stringsTask[4], StatusTask.valueOf(stringsTask[3]),
                    TypeTask.valueOf(stringsTask[1]));
        }else {

            // Еще один конструктор был создан специально под SubTask, т.к. у него есть поле epicId, которого нет в Task
            task = new Task(Integer.parseInt(stringsTask[0]), stringsTask[2], stringsTask[4], StatusTask.valueOf(stringsTask[3]),
                    TypeTask.valueOf(stringsTask[1]), Integer.valueOf(stringsTask[5]));
        }
        return task;
    }

    // метод преобразования Истории в строку целых чисел
    public static String historyToString(HistoryManager historyManager) {

        StringBuilder str = new StringBuilder();
        List<Task> history = historyManager.getHistory();
        String delimeter = "";

        // это условие нужно для того чтобы метод Save использовался в создании задач, то не выскакивала ошибка нуля
        if (history == null) return "";

        for (Task task : history) {
            str.append(delimeter);
            delimeter = ",";
            str.append(task.getId());

        }

        return new String(str);
    }

    // Метод преобразования Истории из строки
    public static List<Integer> historyFromString(String value) {
        String[] historyStr = value.split(",");
        List<Integer> history = new ArrayList<>();

        for (int i = 0; i < historyStr.length; i++) {
            history.add(i, Integer.valueOf(historyStr[i]));
        }
        return history;
    }

    // метод сохранения всех добавленных и просмотренных задач
    public void save() throws ManagerSaveException {
        try {

            // Почему в т.з. file? Приходится полный путь писать...
            Writer fileWriter = new FileWriter("C:\\Users\\Angelina\\dev\\java-kanban\\src\\Task.csv");
            fileWriter.write("id,type,name,status,description,epic\n");

            // Циклы заполнения файла созданными задачами
            for (Integer integer : tasks.keySet()) {
                fileWriter.write(toString(tasks.get(integer))+"\n");
            }
            for (Integer integer : epics.keySet()) {
                fileWriter.write(toString(epics.get(integer))+"\n");
            }
            for (Integer integer : subTaskHashMap.keySet()) {
                fileWriter.write(toString(subTaskHashMap.get(integer))+"\n");
            }

            // Отступ и строка истории просмотра
            fileWriter.write("\n");
            fileWriter.write(historyToString(Managers.getDefaultHistory()));
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
    }

    // Метод загрузки задач из бекапа
    static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            FileBackedTasksManager loadFromFile = new FileBackedTasksManager();
            HistoryManager historyManager = new InMemoryHistoryManager();

            // Эта мапа нужна для сохранения всех задач в типе Task, так более удобно выгружать историю
            HashMap<Integer, Task> allTasks = new HashMap<>();
            br.readLine();

            while (br.ready()) {

                String str = br.readLine();

                // ловим пустую строку между задачами и историей
                if (str.equals("")) break;
                Task task = fromString(str);
                allTasks.put(task.getId(), task);

                // Разбиваем задачи по типам.
                if (task.getTypeTask() == TypeTask.Task) {
                    loadFromFile.tasks.put(task.getId(), task);

                    // добавляем задачу в ту хэш-мапу, которая соответствует типу задачи
                } else if (task.getTypeTask() == TypeTask.Epic) {
                    Epic epic = new Epic(task.getId(), task.getName(), task.getDescription(), task.getStatus());
                    epic.setId(task.getId());
                    loadFromFile.epics.put(epic.getId(), epic);

                } else if (task.getTypeTask() == TypeTask.SubTask) {
                    SubTask subTask = new SubTask(task.getId(), task.getName(), task.getDescription(), task.getStatus(),
                            SubTask.getEpicsID());
                    loadFromFile.epics.get(SubTask.getEpicsID()).getSubTasks().add(subTask.getId());
                    subTask.setId(task.getId());
                    loadFromFile.subTaskHashMap.put(subTask.getId(), subTask);
                }
            }
            List<Integer> history = historyFromString(br.readLine());
            for (Integer integer : history) {
                historyManager.add(allTasks.get(integer));
            }
            reader.close();
            return loadFromFile;
        }catch (IOException e){
            System.out.println("Ошибка чтения файла");
            return null;
        }
    }
    public static void main(String[] args) throws IOException {
        FileBackedTasksManager file = new FileBackedTasksManager();
        file.createTask(new Task("Купить молоко", "Сходить в ближайший магазин и взять молоко", StatusTask.NEW));
        file.createTask(new Task("Заплатить за квартиру", "Кварплата", StatusTask.DONE));
        file.createEpic(new Epic("Переезд", "Переезд в другую квартиру", StatusTask.NEW));
        file.createEpic(new Epic("Ремонт", "Ремонт в новой квартире", StatusTask.NEW));
        file.createSubTasks(3,new SubTask("Собрать коробки","Коробки для перезда не собраны",StatusTask.DONE));
        file.createSubTasks(3,new SubTask("Упаковать вещи", "Вещи в коробку!", StatusTask.DONE));
        file.createSubTasks(3,new SubTask("Сказать слова прощания", "Молитву", StatusTask.DONE));
        file.getTaskById(1);
        file.getEpicById(3);
        file.getSubTaskById(5);
        file.removeTaskById(1);
        File file1 = new File("C:\\Users\\Angelina\\dev\\java-kanban\\src\\Task.csv");
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(file1);
        System.out.println(fileBackedTasksManager.epics.keySet());
        System.out.println(fileBackedTasksManager.tasks.keySet());
        System.out.println(fileBackedTasksManager.subTaskHashMap.keySet());
        System.out.println(Managers.getDefaultHistory().getHistory());
        System.out.println("");

    }

}

