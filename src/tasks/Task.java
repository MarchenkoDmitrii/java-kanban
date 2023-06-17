package tasks;
import managers.InMemoryTaskManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {

    public Integer epicId;
    private int id;
    private String name;
    private String description;
    private StatusTask status;
    private TypeTask typeTask;
    private  Long duration;
    protected LocalDateTime startTime;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public String stringStartTime(){
        return startTime == null ? "" : startTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public void setStartTime(String startTime) {
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public Task(String name, String description, StatusTask status, Long duration, String startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = TypeTask.valueOf(this.getClass().getSimpleName());
        this.duration = duration;
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
    public Task(int id, String name, String description, StatusTask status, TypeTask typeTask, Long duration, String startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = typeTask;
        this.duration = duration;
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public Task(int id, String name, String description, StatusTask status, TypeTask typeTask, Long duration,
                String startTime, Integer epicId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = typeTask;
        this.duration = duration;
        this.startTime = Objects.equals(startTime, "") ?
                null : LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        this.epicId = epicId;
    }

    public Task(int id) {
        this.id = id;
    }

    public Task(int id, Long duration, String startTime) {
        this.id = id;
        this.duration = duration;
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public Task(int id, String name, String description, StatusTask status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = 0L;
    }

    public Task(String name, String description, StatusTask status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = 0L;
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

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
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
