package model.ADTs;

import java.util.Collection;
import java.util.Map;

public interface MyHeapInterface<T> {
    int allocate(T value);
    T deallocate(int address);

    T get(int address);
    void put(int address, T value);
    boolean exists(int address);

    Map<Integer, T> getContent();
    void setContent(Map<Integer, T> map);
    Collection<T> valuesAsCollection();
}
