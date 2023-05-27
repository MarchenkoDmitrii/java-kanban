package tasks;
import managers.InMemoryTaskManager;

public class Task {

    private int id;
    private String name;
    private String description;
    private StatusTask status;
    private TypeTask typeTask;

    public Task(String name, String description, StatusTask status) {
        this.id = InMemoryTaskManager.getIdCounter();
        InMemoryTaskManager.setIdCounter(id+1);
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = TypeTask.valueOf(this.getClass().getSimpleName());
    }
    public Task(int id, String name, String description, StatusTask status, TypeTask typeTask) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = typeTask;
    }

    public Task(int id, String name, String description, StatusTask status, TypeTask typeTask, Integer epicId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = typeTask;
        SubTask.setEpicsID(epicId);
    }

    public Task(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public StatusTask getStatus() {
        return status;
    }
    public void setStatus(StatusTask status) {
        this.status = status;
    }
    public TypeTask getTypeTask() {
        return typeTask;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeTask(TypeTask typeTask) {
        this.typeTask = typeTask;
    }
}
