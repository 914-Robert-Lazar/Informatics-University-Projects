package model.ADTs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<T> implements MyHeapInterface<T>{
    AtomicInteger freeLocation; // An int value that may be updated atomically
    Map<Integer, T> heap;

    public MyHeap() {
        this.heap = new HashMap<>();
        this.freeLocation = new AtomicInteger(0);

    }

    @Override
    public int allocate(T value) {
        this.heap.put(this.freeLocation.incrementAndGet(), value);
        return this.freeLocation.get();
    }

    @Override
    public T deallocate(int address) {
        return this.heap.remove(address);
    }

    @Override
    public T get(int address) {
        return this.heap.get(address);
    }

    @Override
    public void put(int address, T value) {
        this.heap.put(address, value);
    }

    @Override
    public boolean exists(int address) {
        return this.heap.containsKey(address);
    }

    @Override
    public Map<Integer, T> getContent() {
        return this.heap;
    }

    @Override
    public void setContent(Map<Integer, T> map) {
        this.heap = map;
    }

    @Override
    public Collection<T> valuesAsCollection() {
        return this.heap.values();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem: this.heap.keySet()) {
            if (elem != null)
                s.append(elem.toString()).append(" -> ").append(this.heap.get(elem).toString()).append('\n');
        }
        return s.toString();
    }

}
