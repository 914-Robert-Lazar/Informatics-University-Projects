package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.util.Map;

public interface IProcedureTable<Val> {
    public Val get(String name);
    public void put(String name, Val value);
    public Map<String, Val> getContent();
    public boolean exists(String name);
    public void setContent(Map<String, Val> map);
}
