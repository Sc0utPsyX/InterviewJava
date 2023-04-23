package homework2.second;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayListImpl<T> implements ArrayList<T>, Iterable<T>{

    private final int START_SIZE = 16;
    private final int RESIZE_RATE = 4;
    private Object[] array = new Object[START_SIZE];
    private int pointer = 0;
    @Override
    public void add(T t) {
        if (pointer == array.length - 1) {
            resize(array.length * 2);
        }
        array[pointer++] = t;
    }

    @Override
    public void clear() {
        array = new Object[START_SIZE];
    }

    @Override
    public boolean contains(T t) {
        for (int i = 0; i < pointer; i++) {
            if (array[i] == t) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(int index) {
        if (index < pointer) {
            return (T) array[index];
        } else {
            throw new NoSuchElementException("Элемент не найден");
        }
    }

    @Override
    public int indexOf(T t) {
        for (int i = 0; i < pointer; i++) {
            if (array[i] == t) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void remove(int index) {
        for (int i = index; i < pointer; i++)
            array[i] = array[i+1];
        array[pointer] = null;
        pointer--;
        if (array.length > START_SIZE && pointer < array.length / RESIZE_RATE){
            resize(array.length/2);
        }
    }

    @Override
    public void remove(T t) {
        int index = indexOf(t);
        if (index != -1){
            remove(index);
        }
    }

    public int size(){
        return pointer;
    }

    public void resize(int newSize) {
        Object[] newArray = new Object[newSize];
        System.arraycopy(array, 0, newArray, 0, pointer);
        array = newArray;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomArrayListIterator();
    }

    public class CustomArrayListIterator implements Iterator<T>{
        public int counter = 0;
        @Override
        public boolean hasNext() {
            return counter <= pointer;
        }

        @Override
        public T next() {
            if (hasNext()){
                return (T) array[counter++];
            }
            return null;
        }
    }
}
