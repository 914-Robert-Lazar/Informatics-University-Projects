package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReturnStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        programState.getSymbolTableStack().pop();
        programState.setSymTable(programState.getSymbolTableStack().top());
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "return";
    }
}
