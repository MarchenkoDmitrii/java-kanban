import java.util.ArrayList;

public class Epic extends Task {
//    Лист с подзадачами
    private final ArrayList<Integer> subTasks;

    public Epic(String name, String description, String status) {
        super(name, description, status);
        this.status = status;
        subTasks = new ArrayList<>();
    }
    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }


    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}