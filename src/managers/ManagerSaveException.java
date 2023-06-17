package managers;

import java.io.IOException;

public class ManagerSaveException extends IOException {
    public ManagerSaveException(String errorSavingTasks, Exception e){

    }
    public ManagerSaveException(final String message){
        super(message);
    }
}
