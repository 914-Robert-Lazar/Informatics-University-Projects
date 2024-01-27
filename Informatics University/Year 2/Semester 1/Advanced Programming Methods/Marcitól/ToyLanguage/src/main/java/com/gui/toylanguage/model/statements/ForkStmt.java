package com.gui.toylanguage.model.statements;



import com.gui.toylanguage.adt.*;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.Value;

import java.io.BufferedReader;
import java.util.HashMap;

public class ForkStmt implements IStmt{
    private IStmt stmt;

    public ForkStmt(IStmt stmt){
        this.stmt = stmt;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyHeapDictionary heapTable = state.getHeapTable();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();

        MyIDictionary<String, Value> newSymTable = new MyDictionary<String, Value>();
        newSymTable.setDictionary((HashMap<String, Value>) symTable.getDictionary().clone()); //deepcopy the symtable(dictionary), this will be the forks independent symtable
        MyIStack<IStmt> newExeStack = new MyStack<IStmt>();

        return new PrgState(newExeStack, newSymTable, out, fileTable, heapTable, stmt);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        MyIDictionary<String, Type> newTypeEnv = new MyDictionary<String, Type>();
        newTypeEnv.setDictionary((HashMap<String, Type>) typeEnv.getDictionary().clone());
        stmt.typecheck(newTypeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }

    @Override
    public String toString(){
        return "fork ( " + stmt.toString() + " );";
    }
}
