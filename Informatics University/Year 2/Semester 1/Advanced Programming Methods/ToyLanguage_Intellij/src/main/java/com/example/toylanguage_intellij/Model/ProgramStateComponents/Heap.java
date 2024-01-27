package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.util.HashMap;
import java.util.Map;

public class Heap<Val> implements IHeap<Val> {
    public static int nextAddress = 0;
    protected Map<Integer, Val> map;


    public Heap() {
        this.map = new HashMap<Integer, Val>();
    }

    private int nextFreeAddress() {
        return ++nextAddress;
    }
    @Override
    public int put(Val value) {
        map.put(nextFreeAddress(), value);
        return nextAddress;
    }

    @Override
    public void setContent(Map<Integer, Val> map) {
        this.map = map;
    }

    @Override
    public Map<Integer, Val> getContent() {
        return this.map;
    }

    @Override 
    public String toString() {
        return "Heap: " + map.toString();
    }

    @Override
    public boolean isDefined(Integer key) {
        return map.get(key) != null;
    }

    @Override
    public Val findValue(Integer key) {
        return map.get(key);
    }

    @Override
    public void update(int address, Val value) {
        map.put(address, value);
    }
}
