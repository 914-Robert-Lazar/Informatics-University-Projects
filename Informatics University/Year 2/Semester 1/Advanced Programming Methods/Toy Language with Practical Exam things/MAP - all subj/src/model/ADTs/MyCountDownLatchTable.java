package model.ADTs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyCountDownLatchTable<T> implements MyCountDownLatchTableInterface<T>{
    AtomicInteger freeLocation; // An int value that may be updated atomically
    Map<Integer, T> latchTable;

    public MyCountDownLatchTable() {
        this.latchTable = new HashMap<>();
        this.freeLocation = new AtomicInteger(0);
    }

    @Override
    public synchronized int allocate(T value) {
        this.latchTable.put(this.freeLocation.incrementAndGet(), value);
        return this.freeLocation.get();
    }

    @Override
    public synchronized T lookup(int address) {
        return this.latchTable.get(address);
    }

    @Override
    public void update(int address, T value) {
        this.latchTable.put(address, value);
    }

    @Override
    public boolean exists(int address) {
        return this.latchTable.containsKey(address);
    }

    @Override
    public Map<Integer, T> getContent() {
        return this.latchTable;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem: this.latchTable.keySet()) {
            if (elem != null)
                s.append(elem.toString()).append(" -> ").append(this.latchTable.get(elem).toString()).append('\n');
        }
        return s.toString();
    }
}
