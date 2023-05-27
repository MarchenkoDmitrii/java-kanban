
package managers;

import tasks.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    // Хэшмап с узлами и идентификаторами задач
    protected static Map<Integer, Node> idNode = new HashMap<>();
    private static Node<Task> head;
    private static Node<Task> tail;

    private static int size = 0;

    // Метод добавления задачи в историю
    @Override
    public void add(Task task) {
            // Если в хэшмап есть ключ с таким же id, то удаляем из двусвязанного списка и из Хэшмап
            if (idNode.containsKey(task.getId())) {
                removeNode(idNode.get(task.getId()));
                idNode.remove(task);
                size--;
            }
            // Добавление задачи в список и в хэшмап
            linkLast(task);
            idNode.put(task.getId(), tail);
        }

    @Override
    public void remove(int id) {
        removeNode(idNode.get(id));
        idNode.remove(id);

    }

    @Override
    public List<Task> getHistory() {
        return getTask();
    }


    public void linkLast(Task element) {
        // создание нового узла
        tail = new Node<>(element, null, tail);
        // Если узел первый
        if (head == null) head = tail;
        size++;
    }

    // метод преобразования двусвязанного списка в ArrayList
    public ArrayList<Task> getTask() {
        // Создадим список и два узла: текущий(изначально головно) и хвост
        ArrayList<Task> tasks = new ArrayList<>();
        if (head == null) return null;

        Node<Task> current = head;

        // Пока счетчик не равен размеру, заполняем Arraylist
        while (current != null) {
            tasks.add(current.data);
            current = current.nextElement;
        }
        return tasks;
    }

    public void removeNode(Node<Task> del) {
        // Проверка на пустой список
        if (head == null || del == null)
        {
            return;
        }

        // если удаляемый узел головной
        if (head == del)
        {
            head = del.nextElement;
        }

        // если удаляемый узел не последний
        if (del.nextElement != null)
        {
            del.nextElement.previousElement = del.previousElement;
        }

        // если удаляемый узел не первый
        if (del.previousElement != null)
        {
            del.previousElement.nextElement = del.nextElement;
        }

    }

}