package team.mediasoft.education.kvv.list;

class Node<E> {
    private E value;
    private Node<E> previous;
    private Node<E> next;

    E getValue() {
        return value;
    }

    void setValue(E value) {
        this.value = value;
    }

    Node<E> getPrevious() {
        return previous;
    }

    void setPrevious(Node<E> previous) {
        this.previous = previous;
    }

    Node<E> getNext() {
        return next;
    }

    void setNext(Node<E> next) {
        this.next = next;
    }
}
