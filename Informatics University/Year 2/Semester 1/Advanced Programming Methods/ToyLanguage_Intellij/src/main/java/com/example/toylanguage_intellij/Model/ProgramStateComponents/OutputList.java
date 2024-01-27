package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.util.LinkedList;
import java.util.List;

public class OutputList<T> implements IOutputList<T> {
    private List<T> outputList;

    public OutputList() {
        outputList = new LinkedList<>();
    }

    @Override
    public void add(T element) {
        outputList.add(element);
    }

    @Override
    public void clear() {
        outputList.clear();
    }
    
    @Override
    public String toString() {
        return "MyList{" +
                "output=" + outputList +
                '}';
    }

    public List<T> getOutput() {
        return outputList;
    }

    public void setOutput(List<T> output) {
        this.outputList = output;
    }
}
