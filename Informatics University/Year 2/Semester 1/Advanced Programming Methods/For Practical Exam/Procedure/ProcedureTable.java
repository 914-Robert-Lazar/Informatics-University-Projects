package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.util.HashMap;
import java.util.Map;

public class ProcedureTable<Val> implements IProcedureTable<Val> {
    Map<String, Val> procTable;

    public ProcedureTable() {
        procTable = new HashMap<>();
    }

    @Override
    public Val get(String name) {
        return procTable.get(name);
    }

    @Override
    public void put(String name, Val value) {
        procTable.put(name, value);
    }

    @Override
    public Map<String, Val> getContent() {
        return procTable;
    }

    @Override
    public boolean exists(String name) {
        return procTable.containsKey(name);
    }

    @Override
    public void setContent(Map<String, Val> map) {
        procTable = map;
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem: procTable.keySet()) {
            if (elem != null)
                s.append(elem).append(" -> ").append(procTable.get(elem).toString()).append('\n');
        }
        return s.toString();
    }
}
