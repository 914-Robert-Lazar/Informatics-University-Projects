package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.util.HashMap;
import java.util.Map;

public class LatchTable<Val> implements ILatchTable<Val>{
    public static int nextAddress = 0;
    protected Map<Integer, Val> map;

    public LatchTable() {
        this.map = new HashMap<Integer, Val>();
    }
    private int nextFreeAddress() {
        return ++nextAddress;
    }
    @Override
    public synchronized int put(Val value) {
        this.map.put(nextFreeAddress(), value);
        return nextAddress;
    }

    @Override
    public boolean isDefined(int address) {
        return this.map.containsKey(address);
    }

    @Override
    public synchronized Val findValue(int address) {
        return map.get(address);
    }

    @Override
    public void update(int address, Val value) {
        map.put(address, value);
    }

    @Override
    public Map<Integer, Val> getContent() {
        return this.map;
    }
}
