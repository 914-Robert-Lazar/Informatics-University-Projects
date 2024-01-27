package typecheck.model.adt;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class MyStack<T> implements MyIStack<T>{

    private Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public void push(T elem) {
        stack.push(elem);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }


    @Override
    public String toString() {
        return "MyStack:"+getReversed();
    }

    private String getReversed(){
        List<T> l=stack.stream().collect(Collectors.toList());
        Collections.reverse(l);
        return l.toString();
    }
}
