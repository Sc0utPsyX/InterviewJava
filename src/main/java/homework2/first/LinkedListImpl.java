package homework2.first;

import java.util.Iterator;

public class LinkedListImpl<T> implements LinkedList<T>, Iterable<T> {

    protected class Node<T> {
        protected T value;
        protected Node<T> next;
        protected Node<T> prev;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    protected Node<T> head;
    protected Node<T> tail;
    protected int listLength;

    public LinkedListImpl() {

    }


    @Override
    public void addLast(T element) {
        if (head == null) {
            addFirst(element);
            return;
        }
        Node<T> newLastNode = new Node<>(element);
        Node<T> last = tail;
        last.next = newLastNode;
        newLastNode.prev = last;
        tail = newLastNode;
        listLength++;
    }

    @Override
    public void addFirst(T element) {
        Node<T> newHead = new Node<>(element);
        if (head == null) {
            head = newHead;
            tail = newHead;
            listLength++;
            return;
        }
        newHead.prev = null;
        newHead.next = head;
        head.prev = newHead;
        head = newHead;
        listLength++;
    }

    @Override
    public T getFirst() {
        return extractNodeValue(head);
    }

    @Override
    public T getLast() {
        return extractNodeValue(tail);
    }

    protected T extractNodeValue(Node<T> node) {
        return node == null ? null : node.value;
    }

    protected Node<T> getNode(int index) {
        if (head == null) {
            throw new IllegalArgumentException("Index " + index + " out of bounds");
        }
        int counter;
        Node<T> currentNode;
        if (index > listLength / 2){
            counter = listLength - 1;
            currentNode = tail;
            while (counter != index && currentNode.prev != null) {
                currentNode = currentNode.prev;
                counter--;
            }
        } else {
        currentNode = head;
        counter = 0;
        while (counter != index && currentNode.next != null) {
            currentNode = currentNode.next;
            counter++;
        }
        }
        if (counter != index) {
            throw new IllegalArgumentException("Index " + index + " out of bounds");
        }
        return currentNode;
    }

    @Override
    public void add(T element, int index) {
        if (index == 0) {
            addFirst(element);
        }
        if (index == listLength - 1) {
            addLast(element);
        }
        Node<T> node = getNode(index - 1);

        Node<T> newNextNode = node.next;
        Node<T> newNode = new Node<>(element, node, newNextNode);
        node.next = newNode;
    }

    @Override
    public T get(int index) {
        return extractNodeValue(getNode(index));
    }

    @Override
    public void delete(int index) {
        if (index == 0) {
            if (head == null) {
                throw new IllegalArgumentException("Index " + index + " out of bounds");
            }
            head = head.next;
            return;
        }
        if (index == listLength - 1){
            tail = tail.prev;
            listLength--;
            return;
        }
        Node<T> node = getNode(index - 1);
        Node<T> nodeForRemove = node.next;
        if (nodeForRemove == null) {
            throw new IllegalArgumentException("Index " + index + " out of bounds");
        }
        Node<T> newNextNode = nodeForRemove.next;
        newNextNode.prev = node;
        node.next = newNextNode;
        listLength--;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomListIterator();
    }

    public class CustomListIterator implements Iterator<T>{
        public Node<T> currentNode = head;
        @Override
        public boolean hasNext() {
         return currentNode != null;
        }

        @Override
        public T next() {
            if (hasNext()){
                T value = currentNode.value;
                currentNode = currentNode.next;
                return value;
            }
            return null;
        }

        @Override
        public void remove() {
            if (hasNext()){
                if (currentNode.prev == null ) {
                    if (currentNode.next != null){
                    head = currentNode.next;
                    head.prev = null;
                    listLength--;
                    }
                }
                if (listLength == 1){
                    head = null;
                }
                currentNode = currentNode.next;
            }
        }
        public void remove(T node) {
            if (currentNode.value.equals(node)){
                if (currentNode.prev == null ) {
                    if (currentNode.next != null){
                        head = currentNode.next;
                        head.prev = null;
                        listLength--;
                        currentNode = currentNode.next;
                        return;
                    }
                }
                if (currentNode.next == null){
                    currentNode.prev.next = null;
                    currentNode = null;
                    listLength--;
                    return;
                }
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                currentNode = currentNode.next;
                listLength--;
                return;
                }
            currentNode = currentNode.next;
            return;
            }
        }



    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        Node<T> iterator = head;
        while (iterator != null) {
            output.append(iterator.value).append(" <-> ");
            iterator = iterator.next;
        }
        if (output.length() > 5) {
            for (int i = 0; i < 5; i++) {
                output.deleteCharAt(output.length() - 1);
            }
        }
        return "[" + output + "]";
    }
}
