package model;

import exception.InterpreterException;
import model.adt.DictionaryInterface;
import model.adt.HeapInterface;
import model.adt.ListInterface;
import model.adt.StackInterface;
import model.statements.Statement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ProgramState {
    //TODO: id fix haver
    private StackInterface<Statement> executionStack;
    private DictionaryInterface<String, Value> symbolsTable;
    private ListInterface<Value> out;
    private DictionaryInterface<StringValue, BufferedReader> fileTable;
    private HeapInterface<Integer,Value> heapTable;
    private static int id=0;
    private int fId;
    private Statement originalProgram;
    public ProgramState(StackInterface<Statement> _ex,DictionaryInterface<String,Value> _st,ListInterface<Value> o,DictionaryInterface<StringValue,BufferedReader> fileTable,HeapInterface<Integer,Value> heapTable,Statement statement) throws InterpreterException {
        executionStack=_ex;
        symbolsTable=_st;
        out=o;
        this.fileTable=fileTable;
        this.heapTable=heapTable;
        originalProgram=statement.deepCopy();
        executionStack.push(originalProgram);
        id=newId();
        fId=id;
    }
    public ProgramState(StackInterface<Statement> _ex,DictionaryInterface<String,Value> _st,ListInterface<Value> o,DictionaryInterface<StringValue,BufferedReader> fileTable,HeapInterface<Integer,Value> heapTable) throws InterpreterException {
        executionStack=_ex;
        symbolsTable=_st;
        out=o;
        this.fileTable=fileTable;
        this.heapTable=heapTable;
        id=newId();
    }
    public static synchronized Integer newId(){
        return id+1;
    }
    public boolean isNotCompleted(){
        return !executionStack.isEmpty();
    }
    public ProgramState oneStepExecute() throws InterpreterException{
        if (executionStack.isEmpty()){
            throw new InterpreterException("Execution stack is empty");
        }
        Statement currentStatement= executionStack.pop();
        return currentStatement.execute(this);
    }
    public HeapInterface<Integer,Value> getHeapTable(){
        return heapTable;
    }
    public DictionaryInterface<StringValue,BufferedReader> getFileTable(){
        return fileTable;
    }
    public StackInterface<Statement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(StackInterface<Statement> executionStack) {
        this.executionStack = executionStack;
    }

    public DictionaryInterface<String, Value> getSymbolsTable() {
        return symbolsTable;
    }

    public void setSymbolsTable(DictionaryInterface<String, Value> symbolsTable) {
        this.symbolsTable = symbolsTable;
    }

    public ListInterface<Value> getOut() {
        return out;
    }
    public HeapInterface<Integer,Value> getHeap(){
        return heapTable;
    }
    public int getId(){
        return fId;
    }
    public void setHeap(HeapInterface<Integer,Value> newHeap){
        this.heapTable=newHeap;
    }
    public void setOut(ListInterface<Value> out) {
        this.out = out;
    }

    public void setFileTable(DictionaryInterface<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public Statement getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(Statement originalProgram) {
        this.originalProgram = originalProgram;
    }
    public String executionStackToString(){
        StringBuilder buf=new StringBuilder();
        for (Statement st: executionStack.getStack()
             ) {
            buf.append(st);
            buf.append("\n");
        }
        return buf.toString();
    }
    public String symbolsTableToString(){
        StringBuilder buf=new StringBuilder();
        symbolsTable.getDictionary().forEach((key,value)->{
            buf.append(symbolsTable.toStringElem(key,value));
            buf.append("\n");
        });
        return buf.toString();
    }
    public String outToString(){
        StringBuilder buf=new StringBuilder();
        for (Value ex:
             out.getList()) {
            buf.append(ex);
            buf.append("\n");
        }
        return buf.toString();
    }
    public String fileTableOut(){
        StringBuilder buf=new StringBuilder();
        for(Value file:
        fileTable.getDictionary().keySet()){
            buf.append(file);
            buf.append('\n');
        }
        return buf.toString();
    }
    public String heapTableToString(){
        StringBuilder buf=new StringBuilder();
        heapTable.getHeap().forEach((key,value)->{
            buf.append(heapTable.toStringElem(key,value));
            buf.append("\n");
        });
        return buf.toString();
    }

    @Override
    public String toString() {
        return "Id"+fId+"\n"+"ExecutionStack"+"\n"+executionStackToString()+"SymbolsTable"+"\n"+symbolsTableToString()+"Out"+"\n"+outToString()+"FileTable"+"\n"+fileTableOut()+"HeapTable"+"\n"+heapTableToString();
    }
}
