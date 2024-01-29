package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import com.example.toylanguage_intellij.Controller.MyException;

import java.util.List;

public interface IStack<Val> {
    Val pop() throws MyException;
    void push(Val elem);
    boolean isEmpty();
    Val top() throws MyException;
    int size();
    List<Val> getContent();
}
