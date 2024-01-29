package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.*;
import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.IntegerValue;
import com.example.toylanguage_intellij.Model.Values.Value;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AwaitStatement implements IStatement{
    String variableName;
    public AwaitStatement(String variableName) {
        this.variableName = variableName;
    }
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        ILatchTable<Integer> latchTable = programState.getLatchTable();
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();

        if (symbolTable.isDefined(variableName)) {
            Value foundIndex = symbolTable.findValue(variableName);
            if (foundIndex.getType().equals(new IntegerType())) {
                IntegerValue foundIndexInt = (IntegerValue) foundIndex;
                if (latchTable.isDefined(foundIndexInt.getValue())) {
                    if (latchTable.findValue(foundIndexInt.getValue()) != 0) {
                        executionStack.push(this);
                    }
                }
                else {
                    throw new MyException("Variable not found in latch table.");
                }
            }
            else {
                throw new MyException("The value associated with the variable from newLock is not of type int");
            }
        }
        else {
            throw new MyException("The variable in await statement is not in symbol table.");
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.findValue(variableName);
        if(varType.equals(new IntegerType())){
            return typeEnv;
        } else throw new MyException("Variable not of type int");
    }

    @Override
    public String toString(){
        return "await(" + this.variableName + ")";
    }
}
