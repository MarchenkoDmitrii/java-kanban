public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected StatusTask status;



    public Task(String name, String description, StatusTask status) {
        this.id = InMemoryTaskManager.getIdCounter();
        InMemoryTaskManager.setIdCounter(id+1);
        this.name = name;
        this.description = description;
        this.status = status;
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
