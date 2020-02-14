package team.mediasoft.education.kvv.list;

import java.util.Objects;

public class TwoDirectionList<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;

    public int getSize() {
        return size;
    }

    public E getFirst() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("list is empty");
        }
        return first.getValue();
    }

    public E getLast() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("list is empty");
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
            node.setNext(first);
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
            throw new IndexOutOfBoundsException("available index's interval is [0; " + size + "]");
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
        return getNodeByIndex(index).getValue();
    }

    private Node<E> getNodeByIndex(int index) {
        IndexOutOfBoundsException excp = checkIndexInterval(index);
        if (excp != null) {
            throw excp;
        }

        if (index == 0) {
            return first;
        }
        if (index == size - 1) {
            return last;
        }
        return getInner(index);
    }

    private IndexOutOfBoundsException checkIndexInterval(int index) {
        if (isEmpty()) {
            return new IndexOutOfBoundsException("list is empty");
        }
        if ((index < 0) || (index >= size)) {
            return new IndexOutOfBoundsException("available index's interval is [0; " + (size - 1) + "]");
        }
        return null;
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

    /**
     * Gets the first occurrence of the specified element from this list,
     * if it is present. If this list does not contain
     * the element returns false
     * @param element
     * @return null if not exists
     */
    private Node<E> getFirstNodeOf(E element) {
        Node<E> current = this.first;
        while(current != null) {
            if (Objects.equals(element, current.getValue())) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * if not found, returns -1
     * @param element
     * @return
     */
    public int getFirstIndexOf(E element) {
        final int NOT_FOUND = -1;

        int index = 0;
        Node<E> current = this.first;
        while(current != null) {
            if (Objects.equals(element, current.getValue())) {
                return index;
            }
            current = current.getNext();
            index++;
        }

        return NOT_FOUND;
    }

    /**
     * if not found, returns -1
     * @param element
     * @return
     */
    public int getLastIndexOf(E element) {
        final int NOT_FOUND = -1;

        int index = size -1;
        Node<E> current = this.last;
        while (current != null) {
            if (Objects.equals(element, current.getValue())) {
                return index;
            }
            current = current.getPrevious();
            index--;
        }

        return NOT_FOUND;
    }

    public boolean contains(E element) {
        return (getFirstIndexOf(element) != -1);
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present (optional operation).  If this list does not contain
     * the element, it is unchanged.  More formally, removes the element with
     * the lowest index
     *
     * @param element
     * @return true if deleted, or false
     */
    public boolean remove(E element) {
        Node<E> nodeForDelete = getFirstNodeOf(element);
        if (nodeForDelete != null) {
            removeNode(nodeForDelete);
            return true;
        } else {
            return false;
        }
    }

    public E removeByIndex(int index) {
        Node<E> nodeByIndex = getNodeByIndex(index); //IndexOutOfBoundsExcp
        removeNode(nodeByIndex);
        return nodeByIndex.getValue();
    }

    private void removeNode(Node<E> existedNodeForDel) {
        if (size == 1) {
         //it's only element
            first = null;
            last = null;
            size = 0;
        } else {
            if (existedNodeForDel == first) {
                first = existedNodeForDel.getNext();
                first.setPrevious(null);
            } else {
                if (existedNodeForDel == last) {
                    last = existedNodeForDel.getPrevious();
                    last.setNext(null);
                } else {
                    //it's inner node
                    existedNodeForDel.getPrevious().setNext(existedNodeForDel.getNext());
                    existedNodeForDel.getNext().setPrevious(existedNodeForDel.getPrevious());
                }
            }
            size--;
        }
    }

    /**
     *
     * @param newValue
     * @param index
     * @return oldValue
     */
    public E set(E newValue, int index) {
        Node<E> nodeByIndex = getNodeByIndex(index);
        E oldValue = nodeByIndex.getValue();
        nodeByIndex.setValue(newValue);
        return oldValue;
    }

    @Override
    public String toString() {
            StringBuilder presentation = new StringBuilder("TwoDirectionList{" +
                    "first=" + ((first != null) ? String.valueOf(first.getValue()) : "null") +
                    ", last=" + ((last != null) ? String.valueOf(last.getValue()) : "null") +
                    ", size=" + size +
                    '}');

            Node<E> current = first;
            presentation.append("[");
            while (current != null) {
                presentation.append(current.getValue());
                current = current.getNext();
                if (current != null) {
                    presentation.append(", ");
                }
            }
            presentation.append("]");
            return presentation.toString();
    }
}
