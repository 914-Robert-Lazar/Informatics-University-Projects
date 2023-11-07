package Model.ProgramStateComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class ExecutionStack<T> implements IExecutionStack<T>{
    Stack<T> stack;

    public ExecutionStack() {
        stack = new Stack<T>();
    }
    @Override
    public void push(T program) {
        this.stack.push(program);
    }
    @Override
    public boolean isEmpty() {
        return this.stack.empty();
    }
    @Override
    public T pop() {
        return this.stack.pop();
    }
    @Override
    public List<T> getReversed() {
        List<T> list = new ArrayList<>(this.stack);
        Collections.reverse(list);
        return list;
    }
    
    @Override
    public String toString() {
        return "ExecutionStack { " +
                "stack = " + stack +
                '}';
    }

    public Stack<T> getStack() {
        return stack;
    }

    public void setStack(Stack<T> stack) {
        this.stack = stack;
    }
}
