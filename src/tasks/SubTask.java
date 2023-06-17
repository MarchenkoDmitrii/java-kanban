package tasks;

import java.time.LocalDateTime;

public class SubTask extends Task {
    // переменная с идентификатором Эпика, в который входит данная подзадача
     private int epicsID;

    public SubTask(String name, String description, StatusTask status, Long duration, String startTime, Integer epicID) {
        super(name, description,status, duration, startTime);
        setEpicsID(epicID);
        this.setStatus(status);
        this.setTypeTask(TypeTask.valueOf(this.getClass().getSimpleName()));
    }
    public SubTask(int id, String name, String description, StatusTask status, Long duration, String startTime, Integer epicID) {
        super(id,duration,startTime);
        this.setName(name);
        this.setDescription(description);
        this.setId(id);
        this.setStatus(status);
        setEpicsID(epicID);
    }

    public SubTask(String name, String description, StatusTask status, int epicsID) {
        super(name, description, status);
        setEpicsID(epicsID);
        this.setDuration(super.getDuration());
        this.setTypeTask(TypeTask.valueOf(this.getClass().getSimpleName()));
    }

    public int getEpicsID() {
        return epicsID;
    }
     public void setEpicsID(int epicsID) {
        this.epicsID = epicsID;
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
