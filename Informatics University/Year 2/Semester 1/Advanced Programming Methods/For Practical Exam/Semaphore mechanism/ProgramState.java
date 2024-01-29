package com.example.toylanguage_intellij.Model.ProgramStateComponents;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Statements.IStatement;
import com.example.toylanguage_intellij.Model.Values.StringValue;
import com.example.toylanguage_intellij.Model.Values.Value;
import javafx.util.Pair;

public class ProgramState {
    IExecutionStack<IStatement> executionStack;
    IDictionary<String, Value> symbolTable;
    IOutputList<Value> outputList;
    IDictionary<StringValue, BufferedReader> fileTable;
    IHeap<Value> heap;

    ISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable;
    private static int id = 0;
    private final int myId;

    public ProgramState(IExecutionStack<IStatement> executionStack, IDictionary<String, Value> symbolTable, 
                        IOutputList<Value> outputList, IDictionary<StringValue, BufferedReader> fileTable, IHeap<Value> heap,
                        ISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable, IStatement program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.heap = heap;
        this.semaphoreTable = semaphoreTable;
        id = newId();
        this.myId = id;
        this.executionStack.push(program);
    }

    public IExecutionStack<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public void setExecutionStack(IExecutionStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public IDictionary<String, Value> getSymTable() {
        return this.symbolTable;
    }

    public void setSymTable(IDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IOutputList<Value> getOut() {
        return this.outputList;
    }

    public void setOut(IOutputList<Value> outputList) {
        this.outputList = outputList;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setFileTable(IDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IHeap<Value> getHeap() {
        return this.heap;
    }

    public void setHeap(IHeap<Value> heap) {
        this.heap = heap;
    }

    public ISemaphoreTable<Pair<Integer, List<Integer>>> getSemaphoreTable() { return this.semaphoreTable; }

    public void setSemaphoreTable(ISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable) { this.semaphoreTable = semaphoreTable; }

    public boolean isNotCompleted() {
        return !this.executionStack.isEmpty();
    }

    public ProgramState oneStep() throws MyException, IOException {

        if (this.executionStack.isEmpty()) {
            throw new MyException("ProgramState Stack is empty.");
        }

        IStatement currStatement = executionStack.pop();
        return currStatement.execute(this);
    }

    public static synchronized Integer newId() {
        return id + 1;
    }

    public Integer getId() {
        return this.myId;
    }
    @Override
    public String toString() {
        return "ID = " + this.myId + 
                ", ProgramState { exeStack = " + this.executionStack.getReversed() +
                ", symTable = " + this.symbolTable +
                ", out = " + this.outputList +
                ", fileTable = " + this.fileTable +
                ", heapTable = " + this.heap + 
                "}";
    }
}
