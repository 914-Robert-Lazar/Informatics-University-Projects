package com.example.toylanguage_intellij.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Statements.IStatement;
import com.example.toylanguage_intellij.Model.Values.StringValue;
import com.example.toylanguage_intellij.Model.Values.Value;

public class Repository implements IRepository{
    String logFilePath;
    List<ProgramState> programStates;

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
        this.programStates = new Vector<>();
    }

    @Override
    public void add(ProgramState programState) {
        this.programStates.add(programState);
    }

    @Override
    public void logProgramState(ProgramState programState) throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        logFile.println("Thread Id: " + programState.getId());
        logFile.println("ExecutionStack:");
        List<IStatement> executionStack = programState.getExecutionStack().getReversed();
        for (IStatement statement : executionStack) {
            logFile.println(statement.toString());
        }
        logFile.println("SymbolTable:");
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        for (Map.Entry<String, Value> entry : symbolTable.getMap().entrySet()) {
            logFile.println(entry.getKey() + " --> " + entry.getValue());
        }
        logFile.println("OutputList:");
        List<Value> outputList = programState.getOut().getOutput();
        for (Value value : outputList) {
            logFile.println(value);
        }
        logFile.println("FileTable:");
        IDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();
        for (Map.Entry<StringValue, BufferedReader> entry : fileTable.getMap().entrySet()) {
            logFile.println(entry.getKey().toString());
        }
        logFile.println("Heap:");
        Map<Integer, Value> heap = programState.getHeap().getContent();
        for (Map.Entry<Integer, Value> entry : heap.entrySet()) {
            logFile.println(entry.getKey().toString()  + ": " + entry.getValue().toString());
        }
        logFile.println("LatchTable:");
        Map<Integer, Integer> latchTable = programState.getLatchTable().getContent();
        for (Map.Entry<Integer, Integer> entry : latchTable.entrySet()) {
            logFile.println(entry.getKey().toString()  + ": " + entry.getValue().toString());
        }
        logFile.println();
        logFile.close();
    }


    @Override
    public List<ProgramState> getProgramList() {
        return this.programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    
}
