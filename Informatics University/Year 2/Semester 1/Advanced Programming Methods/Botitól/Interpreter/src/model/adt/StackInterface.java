package model.adt;

import exception.InterpreterException;

import java.util.Deque;

public interface StackInterface<T> {
    void push(T elem);
    T pop() throws InterpreterException;
    boolean isEmpty();
    Deque<T> getStack();

}
