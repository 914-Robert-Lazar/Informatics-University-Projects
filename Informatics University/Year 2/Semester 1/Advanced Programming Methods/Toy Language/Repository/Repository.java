package Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.ProgramState;
import Model.Statements.IStatement;
import Model.Values.StringValue;
import Model.Values.Value;

public class Repository implements IRepository{
    Queue<ProgramState> queue;
    String logFilePath;

    public Repository(String logFilePath) {
        this.queue = new LinkedList<ProgramState>();
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState getCurrentProgram() {
        return this.queue.peek();
    }
    

    @Override
    public void add(ProgramState programState) {
        this.queue.add(programState);
    }

    @Override
    public void logProgramState() throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        logFile.println("ExecutionStack:");
        List<IStatement> executionStack = this.getCurrentProgram().getExecutionStack().getReversed();
        for (IStatement statement : executionStack) {
            logFile.println(statement.toString());
        }
        logFile.println("SymbolTable:");
        IDictionary<String, Value> symbolTable = this.getCurrentProgram().getSymTable();
        for (Map.Entry<String, Value> entry : symbolTable.getMap().entrySet()) {
            logFile.println(entry.getKey() + " --> " + entry.getValue());
        }
        logFile.println("OutputList:");
        List<Value> outputList = this.getCurrentProgram().getOut().getOutput();
        for (Value value : outputList) {
            logFile.println(value);
        }
        logFile.println("FileTable:");
        IDictionary<StringValue, BufferedReader> fileTable = this.getCurrentProgram().getFileTable();
        for (Map.Entry<StringValue, BufferedReader> entry : fileTable.getMap().entrySet()) {
            logFile.println(entry.getKey().toString());
        }
        logFile.println("Heap:");
        Map<Integer, Value> heap = this.getCurrentProgram().getHeap().getContent();
        for (Map.Entry<Integer, Value> entry : heap.entrySet()) {
            logFile.println(entry.getKey().toString()  + ": " + entry.getValue().toString());
        }
        logFile.println();
        logFile.close();
    }

    @Override
    public void removeCurrentProgram() {
        this.queue.poll();
    }

    
}
