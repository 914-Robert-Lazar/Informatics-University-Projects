package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.Value;

public class VariableDeclarationStatement implements IStatement {
    String id;
    Type type;

    public VariableDeclarationStatement(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        if (symbolTable.isDefined(id)) {
            throw new MyException("The variable already declared before.");
        }
        else {
            symbolTable.put(id, type.defaultValue());
        }
        
        return null;
    }

    @Override
    public String toString() {
        return type.toString() + " " + id;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(id, type);
        return typeEnv;
    }
    
}
