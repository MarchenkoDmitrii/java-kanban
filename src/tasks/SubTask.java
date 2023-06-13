package tasks;

import java.time.LocalDateTime;

public class SubTask extends Task {
    // переменная с идентификатором Эпика, в который входит данная подзадача
    static private int epicsID;

    public SubTask(String name, String description, StatusTask status, Long duration, String startTime) {
        super(name, description,status, duration, startTime);
        this.setStatus(status);
        this.setTypeTask(TypeTask.valueOf(this.getClass().getSimpleName()));
    }
    public SubTask(int id, String name, String description, StatusTask status, Long duration, String startTime, Integer epicID) {
        super(id,duration,startTime);
        this.setName(name);
        this.setDescription(description);
        this.setId(epicsID);
        this.setStatus(status);
        epicsID = epicID;
    }

    public SubTask(String name, String description, StatusTask status) {
        super(name, description, status);
        this.setDuration(super.getDuration());
        this.setTypeTask(TypeTask.valueOf(this.getClass().getSimpleName()));
    }

    static public int getEpicsID() {
        return epicsID;
    }
    static public void setEpicsID(int epicsID) {
        SubTask.epicsID = epicsID;
    }

    @Override
    public Long getDuration() {
        return super.getDuration();
    }

    @Override
    public LocalDateTime getStartTime() {
        return super.getStartTime();
    }

    @Override
    public String stringStartTime() {
        return super.stringStartTime();
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
