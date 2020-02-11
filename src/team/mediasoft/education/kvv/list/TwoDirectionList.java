package team.mediasoft.education.kvv.list;

import java.util.NoSuchElementException;

public class TwoDirectionList<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;

    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("list is empty");
        }
        return first.getValue();
    }

    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("list is empty");
        }
        return last.getValue();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E addLast(E element) {
        Node<E> node = new Node<>();
        node.setValue(element);
        if (last != null) {
            last.setNext(node);
            node.setPrevious(last);
            last = node;
        } else {
            first = new Node<>();
            first.setValue(element);
            last = first;
        }
        size++;
        return element;
    }

    public E add(E element, int index) {
        if ((index < 0) || (index > size)) {
            throw new IllegalArgumentException("available index's interval is 0 - " + index);
        }
        int mediana = size / 2;
        if (index <= mediana) {
            //go from begin
            int countForSkip = index;
            Node<E> after = first;
            for (int i = 0; i < countForSkip; i++) {
                after = after.getNext();
            }
            Node<E> before = after.getPrevious();

            createNodeBetween(element, before, after);

        } else {
            //go from end
            int countForSkip = size - index;
            Node<E> before = last;
            for (int i = 0; i < countForSkip; i++) {
                before = before.getPrevious();
            }
            Node<E> after = before.getNext();

            createNodeBetween(element, before, after);

        }
        return element;
    }

    private void createNodeBetween(E element, Node<E> before, Node<E> after) {
        Node<E> newNode = new Node<>();
        newNode.setValue(element);

        newNode.setNext(after);
        after.setPrevious(newNode);

        newNode.setPrevious(before);
        before.setNext(newNode);
    }

}
