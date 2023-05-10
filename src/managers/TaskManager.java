package managers;
import tasks.*;
import java.util.Collection;
import java.util.ArrayList;

public interface TaskManager {
    Collection<Task> getAllTasks();
    Collection<Epic> getAllEpic();
    ArrayList<SubTask> getAllSubTask();
    void removeAllTasks();
    void removeAllSubTasks();
    void removeAllEpics();
    Task getTaskById(int id);
    SubTask getSubTaskById(int id);
    Epic getEpicById(int id);
    void createTask(Task task);
    void updateTask(Task task,int id);
    void createEpic(Epic epic);
    void updateEpic(Epic epic, int id);
    void createSubTasks(int epicID,SubTask subTask);
    void updateSubTasks(SubTask subTask, int id);
    void removeTaskById(int id);
    void removeEpicById(int id);
    void removeSubTaskById(int id);
    ArrayList<SubTask> getAllSubTasksByEpic(int epicId);
    void updateEpicStatus(int id);

}
