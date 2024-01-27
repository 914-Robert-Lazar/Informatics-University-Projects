package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.util.List;

public interface IOutputList<T> {
    void add(T element);

    void clear();
    List<T> getOutput();
}
