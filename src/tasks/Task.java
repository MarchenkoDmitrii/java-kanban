package tasks;
import managers.InMemoryTaskManager;

public class Task {

    private int id;
    private String name;
    private String description;
    private StatusTask status;

    public Task(String name, String description, StatusTask status) {
        this.id = InMemoryTaskManager.getIdCounter();
        InMemoryTaskManager.setIdCounter(id+1);
        this.name = name;
        this.description = description;
        this.status = status;
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
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
