package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.util.Map;

public interface IHeap<Val> {
    int put(Val value);
    boolean isDefined(Integer key);
    Val findValue(Integer key);
    void update(int address, Val value);

    void setContent(Map<Integer, Val> map);
    Map<Integer, Val> getContent();
}
