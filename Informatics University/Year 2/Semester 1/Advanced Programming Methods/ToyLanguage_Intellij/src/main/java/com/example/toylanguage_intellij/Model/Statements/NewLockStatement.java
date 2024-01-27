package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ILockTable;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.IntegerValue;
import com.example.toylanguage_intellij.Model.Values.Value;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NewLockStatement implements IStatement {
    String variableName;
    public NewLockStatement(String variableName) {
        this.variableName = variableName;
    }
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        ILockTable<Integer> lockTable = programState.getLockTable();

        if (symbolTable.isDefined(variableName)) {
            Value value = symbolTable.findValue(variableName);
            if (value.getType().equals(new IntegerType())) {
                int newFreeLocation = lockTable.put(-1);
                symbolTable.put(variableName, new IntegerValue(newFreeLocation));
            }
            else {
                throw new MyException("The value associated with the variable from newLock is not of type int");
            }
        }
        else {
            throw new MyException("The variable in new lock statement is not in symbol table.");
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.findValue(variableName);
        if(varType.equals(new IntegerType())){
            return typeEnv;
        } else throw new MyException("Variable not of type int");
    }

    @Override
    public String toString(){
        return "newLock(" + this.variableName + ")";
    }
}
