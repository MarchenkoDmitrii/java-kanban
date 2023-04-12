import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Objects;


public class InMemoryTaskManager implements TaskManager {
    //    Добавляем счетчик-идентификатор задач
    private static int idCounter = 1;
    //    хэш-мап с задачами для внешнего использования
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    //    хэш-мап с подзадачами для промежуточного накопления их, перед присваиванием их эпикам
    private final HashMap<Integer,SubTask> subTaskHashMap = new HashMap<>();
    //    Хэш-мапа эпиков для внешнего использования уже с подзадачами
    protected HashMap<Integer, Epic> epics = new HashMap<>();


    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        InMemoryTaskManager.idCounter = idCounter;
    }
    @Override
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }
    @Override
    public Collection<Epic> getAllEpic() {
        return epics.values();
    }
    @Override
    public ArrayList<SubTask> getAllSubTask() {
//        Создаем лист для выдачи подзадач со всех эпиков
        ArrayList<SubTask> subTaskList = new ArrayList<>();
//        Вытаскиваем из "внешней" хэш-мапы
        for (Integer subTask : subTaskHashMap.keySet()) {
            subTaskList.add(subTaskHashMap.get(subTask));
        }
        return subTaskList;
    }
    @Override
    public void removeAllTasks() {
        tasks.clear();
    }
    @Override
    public void removeAllSubTasks() {
        for (Integer id : epics.keySet()) {
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getSubTasks().size(); i++) {
                epic.getSubTasks().remove(i);
            }
//   Этот метод будет проверять статус Эпика каждый раз, когда происходит изменение числа сабтасков или их удаление
            updateEpicStatus(epic.id);
        }
        subTaskHashMap.clear();
    }
    @Override
    public void removeAllEpics() {
        epics.clear();
        subTaskHashMap.clear();
    }
    @Override
    public Task getTaskById(int id) {
        Managers.getDefaultHistory().add(tasks.get(id));
        return tasks.getOrDefault(id, null);
    }
    @Override
    public SubTask getSubTaskById(int id) {
        return subTaskHashMap.getOrDefault(id, null);
    }
    @Override
    public Epic getEpicById(int id) {

        for (Integer epic: epics.keySet()) {
            if(epic == id){
                return epics.get(id);
            }
        }
        return null;
    }
    @Override
    public void createTask(Task task) {
        tasks.put(task.id, task);
    }
    @Override
    public void updateTask(Task task,int id) {
        task.id = id;
        if (tasks.containsKey(id)) {
            tasks.put(task.id, task);
        }else System.out.println("Задачи с ID: '"+id+"' не существует");
    }
    @Override
    public void createEpic(Epic epic) {
//      Делаем статус Эпика "NЕW" так как он только создан и при отсутствии сабтасков должен быть NEW
        epic.setStatus(StatusTask.NEW);
        epics.put(epic.id, epic);
    }
    @Override
    public void updateEpic(Epic epic, int id) {
        epic.id = id;
//        Так как мы меняем эпик, то из таблицы удалим все подзадачи, Эпик же другой!
        if (epics.containsKey(id)) {
            epics.put(id,epic);
            updateEpicStatus(id);
        }else {
            System.out.println("Эпика с ID: '" + id + "' не существует");
        }
    }

    @Override
    public void createSubTasks(int epicID,SubTask subTask) {
        if (epics.containsKey(epicID))
        {
            subTaskHashMap.put(subTask.id, subTask);
            subTask.setEpicsID(epicID);
            epics.get(epicID).getSubTasks().add(subTask.id);
            updateEpicStatus(subTask.getEpicsID());
        }else {
            System.out.println("Эпика с ID: '" + epicID + "' не существует");
        }
    }

    @Override
    public void updateSubTasks(SubTask subTask, int id) {
        subTask.id = id;
//        Проверяем мапу сабтасков на нужный ключ
        if (subTaskHashMap.containsKey(id)) {
//                Присваиваем новому экземпляру нужный эпик
            subTask.setEpicsID(subTaskHashMap.get(id).getEpicsID());
            subTaskHashMap.put(id, subTask);
//                Идем по мапе эпиков и удаляем нужный нам сабтаск
            for (int i = 0; i < epics.get(subTask.getEpicsID()).getSubTasks().size(); i++) {
                if(epics.get(subTask.getEpicsID()).getSubTasks().get(i).equals(id)){
                    epics.get(subTask.getEpicsID()).getSubTasks().remove(i);
                }
            }
            epics.get(subTask.getEpicsID()).getSubTasks().add(subTask.id);
            updateEpicStatus(subTask.getEpicsID());
        }else {
            System.out.println("Подзадачи с ID: '" + id + "' не существует");
        }
    }

    @Override
    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }else{
            System.out.println( "Задачи с ID: '"+id+"' не существует");
        }

    }

    @Override
    public void removeEpicById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
        }else{
            System.out.println( "Эпика с ID: '"+id+"' не существует");
        }
    }

    @Override
    public void removeSubTaskById(int id) {
        if (subTaskHashMap.containsKey(id)) {
            subTaskHashMap.remove(id);
        }else{
            System.out.println( "Подзадачи с ID: '"+id+"' не существует");
        }
        for (Epic epic : epics.values()) {
            if ((epics.getOrDefault(epic,null) == null)||(epics.get(epic).equals(0))) {
                continue;
            }
            epics.get(epic).getSubTasks().remove(id);
            updateEpicStatus(id);
        }
    }

    @Override
    public ArrayList<SubTask> getAllSubTasksByEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if(epic == null){
            return null;
        }
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (int subTaskId : epic.getSubTasks()) {
            if (epic.getSubTasks().isEmpty()){
                continue;
            }
            subTasks.add(subTaskHashMap.get(subTaskId));
        }
        return subTasks;

    }

    @Override
    public void updateEpicStatus(int id) {
//        Создадим две переменные для проверки статусов у сабтасков
        boolean allSubTasksDone = true;
        boolean allSubTasksNew = true;
        Epic epic = epics.get(id);
//        Если Эпик пустой, то сразу делаем его NEW
        if(epic.getSubTasks().size() == 0){
            epic.setStatus(StatusTask.NEW);
            return;
        }
//        Цикл проходит по всем сабтаскам эпика и делает ложными булевые значения, если они отличаются от DONE или NEW
        for (Integer subTask : epic.getSubTasks()) {
            if (!subTaskHashMap.get(subTask).status.equals(StatusTask.DONE)) {
                allSubTasksDone = false;
            }
            if (!subTaskHashMap.get(subTask).status.equals(StatusTask.DONE)) {
                allSubTasksNew = false;
            }
        }
        if(allSubTasksDone) {
            epic.setStatus(StatusTask.DONE);
        }
        if(allSubTasksNew) {
            epic.setStatus(StatusTask.NEW);
        }
//        Если оба булевы значение ложь, то Эпик будет со статусом "В_РАБОТЕ"
        if(!(allSubTasksDone)||(allSubTasksNew)){
            epic.setStatus(StatusTask.IN_PROGRESS);
        }

    }

    @Override
    public String toString() {
        return "TaskManager{" +
                "tasks=" + tasks +
                ", epics=" + epics +
                ", subTaskHashMap=" + subTaskHashMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManager that = (InMemoryTaskManager) o;
        return tasks.equals(that.tasks) && subTaskHashMap.equals(that.subTaskHashMap) && epics.equals(that.epics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, subTaskHashMap, epics);
    }
}
