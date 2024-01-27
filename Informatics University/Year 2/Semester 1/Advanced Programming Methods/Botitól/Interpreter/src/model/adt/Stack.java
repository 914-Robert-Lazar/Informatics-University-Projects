package model.adt;

import exception.InterpreterException;

import java.util.ArrayDeque;
import java.util.Deque;

public class Stack<T> implements StackInterface<T>{
    private Deque<T> stack;
    public Stack(){
        stack=new ArrayDeque<T>();
    }


    @Override
    public void push(T elem) {
        stack.push(elem);
    }

    @Override
    public T pop() throws InterpreterException {
        if (isEmpty())
            throw new InterpreterException("ExecutionStack Error: The stack is empty");
        else {
            return stack.pop();
        }
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Deque<T> getStack() {
        return stack;
    }


}
