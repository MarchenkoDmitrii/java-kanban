package managers;

public class Managers {
   public static HistoryManager getDefaultHistory(){
       // Возвращаем объект типа InMemoryHistoryManager
       return new InMemoryHistoryManager();
   }
    public static TaskManager getDefault() {
        // Возвращаем объект типа DefaultTaskManager
        return new InMemoryTaskManager() {
        };
    }
}
