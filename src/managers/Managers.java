package managers;

import java.io.IOException;

public class Managers {
    private static final String URL = "http://localhost:8078/";

    public static HistoryManager getDefaultHistory(){
       // Возвращаем объект типа InMemoryHistoryManager
       return new InMemoryHistoryManager();
   }
    public static TaskManager getDefault() {
        // Возвращаем объект типа DefaultTaskManager
        return new InMemoryTaskManager() {
        };
    }
    public static HttpTaskManager getFile() throws IOException, InterruptedException {
        // Возвращаем объект типа InMemoryHistoryManager
        return new HttpTaskManager(URL);
    }
}
