package managers;

import tasks.*;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {


    @Override
    public void removeAllTasks() throws ManagerSaveException {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubTasks() throws ManagerSaveException {
        super.removeAllSubTasks();
        for (Integer integer : epics.keySet()) {
            super.setEpicTime(epics.get(integer));
        }
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
        super.setEpicTime(epics.get(SubTask.getEpicsID()));
        save();
    }

    @Override
    public void updateSubTasks(SubTask subTask, int id) throws ManagerSaveException {
        super.updateSubTasks(subTask, id);
        super.setEpicTime(epics.get(SubTask.getEpicsID()));
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
        int epicsID = getSubTaskById(id).getEpicsID();
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

    public void getPrioritizedTasks() throws ManagerSaveException {
        // Создаем пустой TreeSet с компаратором, который будет сортировать объекты по startTime
        TreeSet<Task> sortedSet = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())));
        // Добавляем все объекты из трех HashMap в TreeSet
        sortedSet.addAll(tasks.values());
        sortedSet.addAll(subTaskHashMap.values());
        LocalDateTime dateTime = sortedSet.first().getStartTime();
        for (Task task : sortedSet) {
            if (dateTime.isAfter(task.getStartTime())) throw new ManagerSaveException("перекрытие задач!");
            dateTime = task.getEndTime();
        }
    }

    // Метод преобразования задачи в строку
    public static String toString(Task task) {

        String result = String.format("%d,%s,%s,%S,%s,%d,%s,%d",
                Optional.ofNullable(task.getId()).orElse(0),
                Optional.ofNullable(task.getTypeTask()).orElse(TypeTask.Task),
                Optional.ofNullable(task.getName()).orElse(""),
                Optional.ofNullable(task.getStatus()).orElse(StatusTask.DONE),
                Optional.ofNullable(task.getDescription()).orElse(""),
                Optional.ofNullable(task.getDuration()).orElse(0L),
                Optional.ofNullable(task.stringStartTime()).orElse(""),
                task.getTypeTask() == TypeTask.SubTask ? SubTask.getEpicsID() : 0);

        return result;
    }

    // Метод преобразования строки в задачу
    public static Task fromString(String value) {

        String[] stringsTask = value.split(",");

        Task task = new Task(
                    Optional.of(Integer.valueOf(stringsTask[0])).orElse(null),
                    Optional.ofNullable(stringsTask[2]).orElse(""),
                    Optional.ofNullable(stringsTask[4]).orElse(""),
                    Optional.of(StatusTask.valueOf(stringsTask[3])).orElse(StatusTask.NEW),
                    Optional.of(TypeTask.valueOf(stringsTask[1])).orElse(TypeTask.Task),
                    Optional.of(Long.valueOf(stringsTask[5])).orElse(0L),
                    Optional.ofNullable(stringsTask[6]).orElse(""),
                    Optional.of(Integer.valueOf(stringsTask[7])).orElse(0)
                    );
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
        try {
            String[] historyStr = value.split(",");
            List<Integer> history = new ArrayList<>();

            for (int i = 0; i < historyStr.length; i++) {
                history.add(i, Integer.valueOf(historyStr[i]));
            }
            return history;
        }catch (NullPointerException e){
            return null;
        }

    }

    // метод сохранения всех добавленных и просмотренных задач
    public void save() throws ManagerSaveException {
        try {

            Writer fileWriter = new FileWriter("C:\\Users\\Angelina\\dev\\java-kanban\\src\\Task.csv");
            fileWriter.write("id, type, name, status, description, duration, startTime, epic\n");

            for (HashMap<Integer, ? extends Task> map : Arrays.asList(tasks, epics, subTaskHashMap)) {
                for (Task value : map.values()) {
                    fileWriter.write(toString(value)+"\n");
                }
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
    public static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {
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
                    tasks.put(task.getId(), task);

                    // добавляем задачу в ту хэш-мапу, которая соответствует типу задачи
                } else if (task.getTypeTask() == TypeTask.Epic) {
                    Epic epic = new Epic(task.getId(), task.getName(), task.getDescription());
                    epic.setId(task.getId());
                    epics.put(epic.getId(), epic);

                } else if (task.getTypeTask() == TypeTask.SubTask) {
                    SubTask subTask = new SubTask(task.getId(), task.getName(), task.getDescription(), task.getStatus(),
                            task.getDuration(),
                            task.getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                            SubTask.getEpicsID());
                    epics.get(SubTask.getEpicsID()).getSubTasks().add(subTask.getId());
                    subTask.setId(task.getId());
                    subTaskHashMap.put(subTask.getId(), subTask);
                }
            }
            List<Integer> history = historyFromString(br.readLine());
            if (history != null) {
                history.stream()
                        .map(allTasks::get)
                        .forEach(historyManager::add);
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
        file.createTask(new Task("Купить молоко", "Сходить в ближайший магазин и взять молоко", StatusTask.NEW,11L,"15.06.1997 16:41"));
        file.createTask(new Task("Заплатить за квартиру", "Кварплата", StatusTask.DONE,60L, "14.06.1997 15:41"));
        file.createEpic(new Epic("Переезд", "Переезд в другую квартиру"));
        file.createEpic(new Epic("Ремонт", "Ремонт в новой квартире"));
        file.createSubTasks(3,new SubTask("Собрать коробки","Коробки для переeзда не собраны",StatusTask.DONE,11L, "17.06.1997 15:55"));
        file.createSubTasks(3,new SubTask("Упаковать вещи", "Вещи в коробку!", StatusTask.NEW,11L, "19.06.1997 15:41"));
        file.createSubTasks(3,new SubTask("Сказать слова прощания", "Молитву", StatusTask.DONE,11L, "18.06.1997 15:41"));
        file.getTaskById(1);
        file.getEpicById(3);
        file.getSubTaskById(5);
        File file1 = new File("C:\\Users\\Angelina\\dev\\java-kanban\\src\\Task.csv");
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(file1);
        System.out.println(Managers.getDefaultHistory().getHistory());
        System.out.println("");

    }

}

