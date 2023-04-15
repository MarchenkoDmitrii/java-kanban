package tasks;
import java.util.ArrayList;

public class Epic extends Task {
//    Лист с подзадачами
    private final ArrayList<Integer> subTasks;

    public Epic(String name, String description, StatusTask status) {
        super(name, description, status);
        this.setStatus(status);
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