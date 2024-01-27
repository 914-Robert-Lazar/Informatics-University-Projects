package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.util.Map;

public interface IDictionary<Key, Val> {
    Val findValue(Key key);

    void put(Key key, Val value);

    boolean isDefined(Key key);

    Map<Key, Val> getMap();

    void remove(Key key);

    public IDictionary<Key, Val> copy();
}
