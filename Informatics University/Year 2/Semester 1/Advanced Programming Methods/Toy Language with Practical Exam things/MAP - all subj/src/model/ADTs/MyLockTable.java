package model.ADTs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLockTable<T> implements MyLockTableInterface<T>{
    AtomicInteger freeLocation; // An int value that may be updated atomically
    Map<Integer, T> lockTable;

    public MyLockTable() {
        this.lockTable = new HashMap<>();
        this.freeLocation = new AtomicInteger(0);
    }

    @Override
    public synchronized int allocate(T value) {
        this.lockTable.put(this.freeLocation.incrementAndGet(), value);
        return this.freeLocation.get();
    }

    @Override
    public synchronized T lookup(int address) {
        return this.lockTable.get(address);
    }

    @Override
    public void update(int address, T value) {
        this.lockTable.put(address, value);
    }

    @Override
    public boolean exists(int address) {
        return this.lockTable.containsKey(address);
    }

    @Override
    public Map<Integer, T> getContent() {
        return this.lockTable;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem: this.lockTable.keySet()) {
            if (elem != null)
                s.append(elem.toString()).append(" -> ").append(this.lockTable.get(elem).toString()).append('\n');
        }
        return s.toString();
    }

}
