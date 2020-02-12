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

    public E addToLastPlace(E element) {
        Node<E> node = new Node<>();
        node.setValue(element);
        if (!isEmpty()) {
            last.setNext(node);
            node.setPrevious(last);
            last = node;
            size++;
            return element;
        } else {
            return addIntoEmptyList(element);
        }
    }

    public E addToFirstPlace(E element) {
        Node<E> node = new Node<>();
        node.setValue(element);
        if (!isEmpty()) {
            node.setNext(node);
            first.setPrevious(node);
            first = node;
            size++;
            return element;
        } else {
            return addIntoEmptyList(element);
        }
    }

    private E addIntoEmptyList(E element) {
        if (size != 0) {
            throw new IllegalStateException("wrong using. list isn't empty");
        }
        first = new Node<>();
        first.setValue(element);
        last = first;
        size = 1;
        return element;
    }

    public E add(E element, int index) {
        if ((index < 0) || (index > size)) {
            throw new IllegalArgumentException("available index's interval is [0; " + size + "]");
        }

        if (index == 0) {
            return addToFirstPlace(element);
        } else {
            if (index == size) {
                return addToLastPlace(element);
            } else {
                return addToInnerPlace(element, index);
            }
        }
    }

    private E addToInnerPlace(E element, int index) {
        Node<E> newNode = new Node<>();
        newNode.setValue(element);

        Node<E> nodeAfter = getInner(index);
        Node<E> nodeBefore = nodeAfter.getPrevious();

        newNode.setPrevious(nodeBefore);
        nodeBefore.setNext(newNode);

        newNode.setNext(nodeAfter);
        nodeAfter.setPrevious(newNode);

        size++;

        return element;
    }

    public E getByIndex(int index) {
        if (isEmpty()) {
            throw new NoSuchElementException("list is empty");
        }
        if ((index < 0) || (index >= size)) {
            throw new IllegalArgumentException("available index's interval is [0; " + (size - 1) + "]");
        }
        if (index == 0) {
            return getFirst();
        }
        if (index == size - 1) {
            return getLast();
        }

        return getInner(index).getValue();
    }

    private Node<E> getInner(int index) {
        int middle = size / 2;
        Node<E> node;
        if (index <= middle) {
            //go from begin
            node = first;
            int countForSkip = index;
            for (int i = 0; i < countForSkip; i++) {
                node = node.getNext();
            }
        } else {
            //go from end
            node = last;
            int countForSkip = size - index - 1;
            for (int i = 0; i < countForSkip; i++) {
                node = node.getPrevious();
            }
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder presentation = new StringBuilder("TwoDirectionList{" +
                "first=" + first.getValue().toString() +
                ", last=" + last.getValue().toString() +
                ", size=" + size +
                '}');

        if (first != null) {
            Node<E> current = first;
            presentation.append("[");
            while (current != null) {
                presentation.append(current.getValue().toString());
                current = current.getNext();
                if (current != null) {
                    presentation.append(", ");
                }
            }
            presentation.append("]");
        }
        return presentation.toString();
    }
}
