package managers;

public class Node<E> {
    public E data;
    public Node<E> nextElement;
    public Node<E> previousElement;

    public Node(E v, Node<E> next, Node<E> previous) {
        data = v;
        nextElement = next;
        if (nextElement != null)
            nextElement.previousElement = this;
        previousElement = previous;
        if (previousElement != null)
            previousElement.nextElement = this;
    }

}