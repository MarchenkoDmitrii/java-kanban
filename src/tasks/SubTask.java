package tasks;

public class SubTask extends Task {
    // переменная с идентификатором Эпика, в который входит данная подзадача
    static private int epicsID;
    private TypeTask typeTask;

    public SubTask(String name, String description, StatusTask status) {
        super(name, description,status);
        this.setStatus(status);
        this.typeTask = TypeTask.SubTask;
    }
    public SubTask(int id, String name, String description, StatusTask status,Integer epicID) {
        super(id);
        this.setName(name);
        this.setDescription(description);
        this.setId(epicsID);
        this.setStatus(status);
        this.epicsID = epicID;
    }

    static public int getEpicsID() {
        return epicsID;
    }
    static public void setEpicsID(int epicsID) {

        SubTask.epicsID = epicsID;
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
