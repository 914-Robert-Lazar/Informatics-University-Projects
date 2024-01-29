package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.util.Map;

public interface ISemaphoreTable<Val> {
    int put(Val value);

    boolean isDefined(int address);
    Val findValue(int address);
    void update(int address, Val value);
    Map<Integer, Val> getContent();
}
