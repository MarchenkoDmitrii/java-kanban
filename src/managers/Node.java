package managers;

import tasks.Task;

public class Node<Task>{
    public Task data;
    public Node<Task> nextElement;
    public Node<Task> previousElement;

    public Node(Task v, Node<Task> next, Node<Task> previous) {
        data = v;
        nextElement = next;
        if (nextElement != null)
            nextElement.previousElement = this;
        previousElement = previous;
        if (previousElement != null)
            previousElement.nextElement = this;
    }

}