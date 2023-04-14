package managers;

import tasks.Task;

import java.util.LinkedList;


public class InMemoryHistoryManager implements HistoryManager{
    private static LinkedList<Task> history = new LinkedList<>();
    @Override
    public void add(Task task) {
        if (history.size() == 10){
            history.removeFirst();
        }
        history.addLast(task);
    }
    @Override
    public LinkedList<Task> getHistory(){
        return  history;
    }


    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "history=" + history +
                '}';
    }
}
