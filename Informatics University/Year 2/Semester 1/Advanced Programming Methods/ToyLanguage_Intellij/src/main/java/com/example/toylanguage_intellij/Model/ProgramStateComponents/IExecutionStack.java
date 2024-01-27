package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.util.List;

public interface IExecutionStack<T> {

    void push(T program);

    boolean isEmpty();

    T pop();

    List<T> getReversed();
    
}
