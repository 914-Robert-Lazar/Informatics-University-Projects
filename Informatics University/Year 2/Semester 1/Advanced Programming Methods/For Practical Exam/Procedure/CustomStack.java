package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import com.example.toylanguage_intellij.Controller.MyException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class CustomStack<Val> implements IStack<Val>{
    final Stack<Val> stack;

    public CustomStack() {
        this.stack = new Stack<>();
    }

    @Override
    public Val pop() throws MyException {
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        return stack.pop();
    }

    @Override
    public void push(Val elem) {
//        if (elem != null)
        stack.push(elem);
    }

    public Val top() throws MyException {
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        return stack.firstElement();
    }

    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        ListIterator<Val> stackIterator = stack.listIterator(stack.size());
        while(stackIterator.hasPrevious()) {
            s.append(stackIterator.previous().toString()).append('\n');
        }
        return s.toString();
    }

    @Override
    public List<Val> getContent() {
        return new ArrayList<>(stack);
    }
}
