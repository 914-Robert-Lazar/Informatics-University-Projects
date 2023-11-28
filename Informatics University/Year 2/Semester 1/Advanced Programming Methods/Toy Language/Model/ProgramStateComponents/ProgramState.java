package Model.ProgramStateComponents;

import java.io.BufferedReader;

import Model.Statements.IStatement;
import Model.Values.StringValue;
import Model.Values.Value;

public class ProgramState {
    IExecutionStack<IStatement> executionStack;
    IDictionary<String, Value> symbolTable;
    IOutputList<Value> outputList;
    IDictionary<StringValue, BufferedReader> fileTable;
    IHeap<Value> heap;

    public ProgramState(IExecutionStack<IStatement> executionStack, IDictionary<String, Value> symbolTable, 
                        IOutputList<Value> outputList, IDictionary<StringValue, BufferedReader> fileTable, IHeap<Value> heap,
                        IStatement program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.heap = heap;

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

    @Override
    public String toString() {
        return "ProgramState { exeStack = " + this.executionStack.getReversed() +
                ", symTable = " + this.symbolTable +
                ", out = " + this.outputList +
                ", fileTable = " + this.fileTable +
                ", heapTable = " + this.heap + 
                "}";
    }
}
