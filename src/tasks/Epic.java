package tasks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Epic extends Task {

//    Лист с подзадачами
    private ArrayList<Integer> subTasks;
    private Long duration;
    private LocalDateTime startTime;
    public Epic(String name, String description) {
        super(name, description);
        this.setStatus(StatusTask.NEW);
        this.setTypeTask(TypeTask.valueOf(this.getClass().getSimpleName()));
        subTasks = new ArrayList<>();
    }
    public Epic(int id, String name, String description) {
        super(id);
        this.setName(name);
        this.setDescription(description);
        this.setTypeTask(TypeTask.valueOf(this.getClass().getSimpleName()));
        this.setStatus(StatusTask.NEW);
        subTasks = new ArrayList<>();
    }

    @Override
    public Long getDuration() {
        return duration;
    }

    @Override
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }


    public String stringStartTime() {
        return startTime == null ? "" : startTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
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