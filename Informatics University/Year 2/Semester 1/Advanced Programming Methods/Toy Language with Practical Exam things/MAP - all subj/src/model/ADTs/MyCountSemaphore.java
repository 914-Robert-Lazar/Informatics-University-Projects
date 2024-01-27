package model.ADTs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyCountSemaphore<T> implements MyCountSemaphoreInterface<T>{
    AtomicInteger freeLocation; // An int value that may be updated atomically
    Map<Integer, T> semaphoreTable;

    public MyCountSemaphore(){
        this.semaphoreTable = new HashMap<>();
        this.freeLocation = new AtomicInteger(0);
    }

    @Override
    public synchronized int allocate(T value) {
        this.semaphoreTable.put(this.freeLocation.incrementAndGet(), value);
        return this.freeLocation.get();
    }

    @Override
    public synchronized T lookup(int address) {
        return this.semaphoreTable.get(address);
    }

    @Override
    public void update(int address, T value) {
        this.semaphoreTable.put(address, value);
    }

    @Override
    public boolean exists(int address) {
        return this.semaphoreTable.containsKey(address);
    }

    @Override
    public Map<Integer, T> getContent() {
        return this.semaphoreTable;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem: this.semaphoreTable.keySet()) {
            if (elem != null)
                s.append(elem.toString()).append(" -> ").append(this.semaphoreTable.get(elem).toString()).append('\n');
        }
        return s.toString();
    }

}
