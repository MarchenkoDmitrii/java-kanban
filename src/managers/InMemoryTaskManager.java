package managers;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {

    //    Добавляем счетчик-идентификатор задач
    private static int idCounter = 1;
    //    хэш-мап с задачами для внешнего использования
    public static  HashMap<Integer, Task> tasks = new HashMap<>();
    //    хэш-мап с подзадачами для промежуточного накопления их, перед присваиванием их эпикам
    public static HashMap<Integer, SubTask> subTaskHashMap = new HashMap<>();
    //    Хэш-мапа эпиков для внешнего использования уже с подзадачами
    public static HashMap<Integer, Epic> epics = new HashMap<>();

    public static int getIdCounter() {
        return idCounter;
    }
    public static void setIdCounter(int idCounter) {
        InMemoryTaskManager.idCounter = idCounter;
    }
    public void setEpicTime(Epic epic){

        epic.setStartTime(getAllSubTasksByEpic(epic.getId()).stream()
                .map(SubTask::getStartTime)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null));
        epic.setDuration(getAllSubTasksByEpic(epic.getId()).stream()
                .filter(duration -> !(duration.getDuration() == null))
                .mapToLong(SubTask::getDuration)
                .sum());
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
    public void removeAllTasks() throws ManagerSaveException {
        tasks.clear();
    }
    @Override
    public void removeAllSubTasks() throws ManagerSaveException {
        for (Integer id : epics.keySet()) {
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getSubTasks().size(); i++) {
                epic.getSubTasks().remove(i);
            }
//   Этот метод будет проверять статус Эпика каждый раз, когда происходит изменение числа сабтасков или их удаление
            updateEpicStatus(epic.getId());
        }
        subTaskHashMap.clear();
    }
    @Override
    public void removeAllEpics() throws ManagerSaveException {
        epics.clear();
        subTaskHashMap.clear();
    }
    @Override
    public Task getTaskById(int id) throws ManagerSaveException {
        if (tasks.getOrDefault(id, null) != null)
            Managers.getDefaultHistory().add(tasks.getOrDefault(id,null));
        return tasks.getOrDefault(id, null);
    }
    @Override
    public SubTask getSubTaskById(int id) throws ManagerSaveException {
        if (subTaskHashMap.getOrDefault(id,null) != null)
            Managers.getDefaultHistory().add(subTaskHashMap.getOrDefault(id,null));
        return subTaskHashMap.getOrDefault(id, null);
    }
    @Override
    public Epic getEpicById(int id) throws ManagerSaveException {
        if(epics.containsKey(id)){
            Managers.getDefaultHistory().add(epics.getOrDefault(id,null));
            return epics.get(id);
            }
        return null;
    }
    @Override
    public void createTask(Task task) throws ManagerSaveException {
        tasks.put(task.getId(), task);
    }
    @Override
    public void updateTask(Task task,int id) throws ManagerSaveException {
        task.setId(id);
        if (tasks.containsKey(id)) {
            tasks.put(task.getId(), task);
        }else System.out.println("Задачи с ID: '"+id+"' не существует");
    }
    @Override
    public void createEpic(Epic epic) throws ManagerSaveException {
//      Делаем статус Эпика "NЕW" так как он только создан и при отсутствии сабтасков должен быть NEW
        epic.setStatus(StatusTask.NEW);
        epics.put(epic.getId(), epic);
    }
    @Override
    public void updateEpic(Epic epic, int id) throws ManagerSaveException {
        epic.setId(id);
//        Так как мы меняем эпик, то из таблицы удалим все подзадачи, Эпик же другой!
        if (epics.containsKey(id)) {
            epics.put(id,epic);
            updateEpicStatus(id);
        }else {
            System.out.println("Эпика с ID: '" + id + "' не существует");
        }
    }
    @Override
    public void createSubTasks(int epicID,SubTask subTask) throws ManagerSaveException {
        if (epics.containsKey(epicID))
        {
            subTaskHashMap.put(subTask.getId(), subTask);
            subTask.setEpicsID(epicID);
            epics.get(epicID).getSubTasks().add(subTask.getId());
            updateEpicStatus(subTask.getEpicsID());
        }else {
            System.out.println("Эпика с ID: '" + epicID + "' не существует");
        }
    }
    @Override
    public void updateSubTasks(SubTask subTask, int id) throws ManagerSaveException {
        subTask.setId(id);
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
            epics.get(subTask.getEpicsID()).getSubTasks().add(subTask.getId());
            updateEpicStatus(subTask.getEpicsID());
        }else {
            System.out.println("Подзадачи с ID: '" + id + "' не существует");
        }
    }
    @Override
    public void removeTaskById(int id) throws ManagerSaveException {
        if (tasks.containsKey(id)) {
            Managers.getDefaultHistory().remove(tasks.getOrDefault(id,null).getId());
            tasks.remove(id);
        }else{
            System.out.println( "Задачи с ID: '"+id+"' не существует");
        }

    }
    @Override
    public void removeEpicById(int id) throws ManagerSaveException {
        if (epics.containsKey(id)) {
            Managers.getDefaultHistory().remove(epics.get(id).getId());
            for (Integer subTask : epics.get(id).getSubTasks()) {
                removeSubTaskById(subTask);
            }
            epics.remove(id);
        }else{
            System.out.println( "Эпика с ID: '"+id+"' не существует");
        }
    }
    @Override
    public void removeSubTaskById(int id) throws ManagerSaveException {
        if (subTaskHashMap.containsKey(id)) {
            Managers.getDefaultHistory().remove(subTaskHashMap.getOrDefault(id,null).getId());
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
    public void updateEpicStatus(int id) throws ManagerSaveException {
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
            if (!subTaskHashMap.get(subTask).getStatus().equals(StatusTask.DONE)) {
                allSubTasksDone = false;
            }
            if (!subTaskHashMap.get(subTask).getStatus().equals(StatusTask.NEW)) {
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
        if((!allSubTasksDone)&&(!allSubTasksNew)){
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
