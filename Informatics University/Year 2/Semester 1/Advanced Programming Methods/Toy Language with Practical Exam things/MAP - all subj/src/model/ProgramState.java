package model;

import exceptions.InterpreterException;
import javafx.util.Pair;
import model.ADTs.*;
import model.Statements.StatementInterface;
import model.Values.StringValue;
import model.Values.Value;

import java.io.BufferedReader;
import java.util.List;

public class ProgramState {
    private MyStackInterface<StatementInterface> exeStack;
    private MyDictionaryInterface<String, Value> symbolTable;
    private MyListInterface<Value> out;
    private MyDictionaryInterface<StringValue, BufferedReader> fileTable;
    private MyHeapInterface<Value> heap;
    private MyLockTableInterface<Integer> lockTable;
    private MyCountDownLatchTableInterface<Integer> latchTable;
    private MyCountSemaphoreInterface<Pair<Integer, List<Integer>>> countSemaphoreTable;
    private MyToySempahoreInterface<Triplet> toySemaphoreTable;
    StatementInterface originalProgram; // optional field, but good to have
    private static int currentID = 1;
    private int id;

    public synchronized void setId() {
        currentID++;
        this.id = currentID;
    }

    public ProgramState(MyStackInterface<StatementInterface> exeStack, MyDictionaryInterface<String, Value> symbolTable,
                        MyListInterface<Value> out, MyDictionaryInterface<StringValue, BufferedReader> fileTable,
                        MyHeapInterface<Value> heap, MyLockTableInterface<Integer> lockTable,
                        MyCountDownLatchTableInterface<Integer> latchTable,
                        MyCountSemaphoreInterface<Pair<Integer, List<Integer>>> countSemaphoreTable,
                        MyToySempahoreInterface<Triplet> toySemaphoreTable,
                        StatementInterface originalProgram) {
        // for main one
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heap = heap;
        this.lockTable = lockTable;
        this.latchTable = latchTable;
        this.countSemaphoreTable = countSemaphoreTable;
        this.toySemaphoreTable = toySemaphoreTable;
        this.id = 1;

        if (this.originalProgram != null) {
            exeStack.push(this.originalProgram);
        }
    }

    public ProgramState(MyStackInterface<StatementInterface> exeStack, MyDictionaryInterface<String, Value> symbolTable,
                        MyListInterface<Value> out, MyDictionaryInterface<StringValue, BufferedReader> fileTable,
                        MyHeapInterface<Value> heap, MyLockTableInterface<Integer> lockTable,
                        MyCountDownLatchTableInterface<Integer> latchTable,
                        MyCountSemaphoreInterface<Pair<Integer, List<Integer>>> countSemaphoreTable,
                        MyToySempahoreInterface<Triplet> toySemaphoreTable) {
        // for fork
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.lockTable = lockTable;
        this.latchTable = latchTable;
        this.countSemaphoreTable = countSemaphoreTable;
        this.toySemaphoreTable = toySemaphoreTable;
    }

    public ProgramState(MyStackInterface<StatementInterface> exeStack, MyDictionaryInterface<String, Value> symbolTable,
                        MyListInterface<Value> out, MyDictionaryInterface<StringValue, BufferedReader> fileTable,
                        MyHeapInterface<Value> heap, StatementInterface originalProgram) {
        // for main one
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = 1;

        if(this.originalProgram != null) {
            exeStack.push(this.originalProgram);
        }
    }


    public ProgramState(MyStackInterface<StatementInterface> exeStack, MyDictionaryInterface<String, Value> symbolTable,
                        MyListInterface<Value> out, MyDictionaryInterface<StringValue, BufferedReader> fileTable,
                        MyHeapInterface<Value> heap) {
        // for fork
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;

    }

    public Integer getId_thread() {
        return this.id;
    }

    public Boolean isNotCompleted(){return !this.exeStack.isEmpty();}

    public ProgramState oneStep() throws InterpreterException {
        if(this.exeStack.isEmpty())
            throw new InterpreterException("prgstate stack is empty");
        StatementInterface currentStatement = this.exeStack.pop();
        return currentStatement.execute(this);
    }

    public void typeCheck() throws InterpreterException{
        originalProgram.typecheck(new MyDictionary<>());
    }

    public MyStackInterface<StatementInterface> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyStackInterface<StatementInterface> exeStack) {
        this.exeStack = exeStack;
    }

    public MyDictionaryInterface<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(MyDictionaryInterface<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public MyListInterface<Value> getOut() {
        return out;
    }

    public void setOut(MyListInterface<Value> out) {
        this.out = out;
    }

    public StatementInterface getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(StatementInterface originalProgram) {
        this.originalProgram = originalProgram;
    }

    public MyDictionaryInterface<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyDictionaryInterface<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public MyHeapInterface<Value> getHeap() {
        return this.heap;
    }

    public void setHeap(MyHeapInterface<Value> heap) {
        this.heap = heap;
    }

    public MyLockTableInterface<Integer> getLockTable() {
        return this.lockTable;
    }

    public void setLockTable(MyLockTableInterface<Integer> lockTable) {
        this.lockTable = lockTable;
    }

    public MyCountDownLatchTableInterface<Integer> getLatchTable() { return this.latchTable; }

    public void setLatchTable(MyCountDownLatchTableInterface<Integer> latchTable) {this.latchTable = latchTable; }

    public MyCountSemaphoreInterface<Pair<Integer, List<Integer>>> getCountSemaphoreTable() { return this.countSemaphoreTable; }

    public void setCountSemaphoreTable(MyCountSemaphoreInterface<Pair<Integer, List<Integer>>> countSemaphoreTable) {this.countSemaphoreTable = countSemaphoreTable; }

    public MyToySempahoreInterface<Triplet> getToySemaphoreTable() { return this.toySemaphoreTable; }

    public void setToySemaphoreTable(MyToySempahoreInterface<Triplet> toySemaphoreTable) {this.toySemaphoreTable = toySemaphoreTable; }

    @Override
    public String toString() {
        //", originalProgram=" + originalProgram +

        return ">>> ProgramState: " + "\n----------\n" +
                "* ID: \n" + Integer.toString(this.id) + "\n----------\n" +
                "* exeStack: \n" + exeStack.toString() + "\n----------\n" +
                "* symbolTable: \n" + symbolTable + "\n----------\n" +
                "* out: " + out + "\n----------\n" +
                "* fileTable=" + fileTable.toString() + "\n----------\n" +
                "* heap: " + heap.toString() + "\n----------\n" +
                "* lockTable: " + lockTable.toString() + "\n----------\n" +
                "* latchTable: " + latchTable.toString() + "\n----------\n" +
                "* countSemaphoreTable: " + countSemaphoreTable.toString() + "\n----------\n" +
                "* toySemaphoreTable: " + toySemaphoreTable.toString() + "\n----------\n\n";
    }

    public ProgramState(MyStackInterface<StatementInterface> exeStack, MyDictionaryInterface<String, Value> symbolTable,
                        MyListInterface<Value> out) {
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
    }
}
