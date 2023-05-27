package tasks;
import java.util.ArrayList;

public class Epic extends Task {
//    Лист с подзадачами
    private final ArrayList<Integer> subTasks;
    private TypeTask typeTask;

    public Epic(String name, String description, StatusTask status) {
        super(name, description, status);
        this.setStatus(status);
        subTasks = new ArrayList<>();
    }
    public Epic(int id, String name, String description, StatusTask status) {
        super(id);
        this.setName(name);
        this.setDescription(description);
        this.setStatus(status);
        this.typeTask = TypeTask.Epic;
        subTasks = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }
    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}