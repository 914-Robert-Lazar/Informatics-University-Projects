package model.ADTs;

import exceptions.AdtException;

import java.util.List;

public interface MyStackInterface<T> {
    T pop() throws AdtException;
    void push(T val);
    boolean isEmpty();
    int size();
    T top() throws AdtException;

    List<T> getAllList();
}
