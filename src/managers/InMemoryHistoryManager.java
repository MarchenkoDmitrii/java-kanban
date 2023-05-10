package managers;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    // Создадим экземпляр двусвязанного списка
    private static CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();
    // Хэшмап с узлами и идентификаторами задач
    private static Map<Integer, Node> idNode = new HashMap<>();


    // Метод добавления задачи в историю
    @Override
    public void add(Task task) {
        // Если в хэшмап есть ключ с таким же id, то удаляем из двусвязанного списка и из Хэшмап
        if (idNode.containsKey(task.getId())) {
            customLinkedList.removeNode(idNode.get(task.getId()));
            idNode.remove(task);
            customLinkedList.size--;
        }
        // Добавление задачи в список и в хэшмап
        customLinkedList.linkLast(task);
        idNode.put(task.getId(), customLinkedList.tail);
    }

    @Override
    public void remove(int id) {
        customLinkedList.removeNode(idNode.get(id));
        customLinkedList.size--;
        idNode.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTask();
    }

    // Класс двусвязанного списка
    public static class CustomLinkedList<T> {
        private Node<T> head;
        private Node<T> tail;

        private int size = 0;
        // Добавление последнего элемента

        public void linkLast(T element) {
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
            Node<Task> current = customLinkedList.head;
            int count = 0;
            // Пока счетчик не равен размеру, заполняем Arraylist
            while (count != customLinkedList.size) {
                tasks.add(current.data);
                current = current.nextElement;
                count++;
            }
            return tasks;
        }

        public void removeNode(Node<T> del) {
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

            return;
        }
    }
}