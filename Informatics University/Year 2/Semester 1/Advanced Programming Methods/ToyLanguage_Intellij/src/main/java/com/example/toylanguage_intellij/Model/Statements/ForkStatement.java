package com.example.toylanguage_intellij.Model.Statements;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ExecutionStack;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.Type;

public class ForkStatement implements IStatement{
    IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        return new ProgramState(new ExecutionStack<IStatement>(), programState.getSymTable().copy(), programState.getOut(), 
                                programState.getFileTable(), programState.getHeap(), programState.getLatchTable(), statement);
    }
    
    @Override
    public String toString() {
        return "Fork(" + statement + ");";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        statement.typeCheck(typeEnv.copy());
        return typeEnv;
    }


}
