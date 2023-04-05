
public class SubTask extends Task {
    // переменная с идентификатором Эпика, в который входит данная подзадачи
    private int epicsID;
    public SubTask(String name, String description,String status) {
        super(name, description,status);
        this.status = status;
    }

    public int getEpicsID() {
        return epicsID;
    }

    public void setEpicsID(int epicsID) {

        this.epicsID = epicsID;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
