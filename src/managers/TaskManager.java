package managers;
import tasks.*;
import java.util.Collection;
import java.util.ArrayList;

public interface TaskManager {
    Collection<Task> getAllTasks();
    Collection<Epic> getAllEpic();
    ArrayList<SubTask> getAllSubTask();
    void removeAllTasks() throws ManagerSaveException;
    void removeAllSubTasks() throws ManagerSaveException;
    void removeAllEpics() throws ManagerSaveException;
    Task getTaskById(int id) throws ManagerSaveException;
    SubTask getSubTaskById(int id) throws ManagerSaveException;
    Epic getEpicById(int id) throws ManagerSaveException;
    void createTask(Task task) throws ManagerSaveException;
    void updateTask(Task task,int id) throws ManagerSaveException;
    void createEpic(Epic epic) throws ManagerSaveException;
    void updateEpic(Epic epic, int id) throws ManagerSaveException;
    void createSubTasks(int epicID,SubTask subTask) throws ManagerSaveException;
    void updateSubTasks(SubTask subTask, int id) throws ManagerSaveException;
    void removeTaskById(int id) throws ManagerSaveException;
    void removeEpicById(int id) throws ManagerSaveException;
    void removeSubTaskById(int id) throws ManagerSaveException;
    ArrayList<SubTask> getAllSubTasksByEpic(int epicId);
    void updateEpicStatus(int id) throws ManagerSaveException;

}
