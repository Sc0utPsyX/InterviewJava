package homework2.second;

import java.util.Iterator;

public interface ArrayList<T> extends Iterable<T> {
    void add(T t);
    void clear();
    boolean contains(T t);
    T get(int index);
    int indexOf(T t);
    void remove(int index);
    void remove(T t);
    Iterator<T> iterator();
}
