package com.gui.toylanguage.model.programState;

import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.adt.MyIList;
import com.gui.toylanguage.adt.MyIStack;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.statements.IStmt;
import com.gui.toylanguage.model.values.Value;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<String, BufferedReader> fileTable;
    MyHeapDictionary heapTable;
    IStmt originalProgram;

    public final Integer id;
    public static Integer lastId = 0;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot, MyIDictionary<String, BufferedReader> flTbl, MyHeapDictionary hpTbl, IStmt prg){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = flTbl;
        heapTable = hpTbl;
        originalProgram = prg.deepCopy();
        exeStack.push(originalProgram);
        id = getNewId();
    }
    private static synchronized Integer getNewId(){
        return ++lastId;
    }
    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() { return this.fileTable; }
    public void setFileTable(MyIDictionary<String, BufferedReader> flTbl) { this.fileTable = flTbl; }

    public MyHeapDictionary getHeapTable() { return this.heapTable; }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public boolean isNotCompleted(){
        if(exeStack.isEmpty())
            return false;
        return true;
    }
    public PrgState oneStep() throws MyException {

        if(exeStack.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public synchronized String toString() {

        String result = "ProgramState id: "+ id.toString() + "\n";

        List<IStmt> programs = exeStack.getReversed();
        result += "Stack {\n";
        for(IStmt program : programs){
            result += program.toString() + "\n";

        }
        result += "} \n";

        result += "SymTable{\n";
        for(Map.Entry<String, Value> entry : symTable.getDictionary().entrySet()){
            result += entry.getKey().toString() + " | " + entry.getValue().toString() + "\n";
        }
        result += "} \n";

        result += "Output {\n";
        for(Value value : out.getList()) {
            result += value.toString() + "\n";
        }
        result += "} \n";

        result += "FileTable {\n";
        for(Map.Entry<String, BufferedReader> entry : fileTable.getDictionary().entrySet()){
            result += entry.getKey().toString() + "\n";
        }
        result += "}\n";

        result += "HeapTable {\n";
        for(Map.Entry<Integer, Value> entry : heapTable.getDictionary().entrySet()){
            result += entry.getKey().toString() + " | " + entry.getValue().toString() + "\n";
        }
        result += "}\n\n";

        return result;
    }
}
