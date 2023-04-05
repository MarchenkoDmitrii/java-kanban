public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected String status;


    public Task(String name, String description, String status) {
        this.id = TaskManager.getIdCounter();
        TaskManager.setIdCounter(id+1);
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
