package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IExecutionStack;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ISemaphoreTable;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.IntegerValue;
import com.example.toylanguage_intellij.Model.Values.Value;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ReleaseStatement implements IStatement {
    String variableName;
    public ReleaseStatement(String variableName) {
        this.variableName = variableName;
    }
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        ISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable = programState.getSemaphoreTable();
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();

        if (symbolTable.isDefined(variableName)) {
            Value foundIndex = symbolTable.findValue(variableName);
            if (foundIndex.getType().equals(new IntegerType())) {
                IntegerValue foundIndexInt = (IntegerValue) foundIndex;
                if (semaphoreTable.isDefined(foundIndexInt.getValue())) {
                    Pair<Integer, List<Integer>> data = semaphoreTable.findValue(foundIndexInt.getValue());

                    data.getValue().remove(programState.getId());
                }
                else {
                    throw new MyException("Variable not found in semaphore table.");
                }
            }
            else {
                throw new MyException("The value associated with the variable from release is not of type int");
            }
        }
        else {
            throw new MyException("The variable in release statement is not in symbol table.");
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
        return "release(" + this.variableName + ")";
    }
}
