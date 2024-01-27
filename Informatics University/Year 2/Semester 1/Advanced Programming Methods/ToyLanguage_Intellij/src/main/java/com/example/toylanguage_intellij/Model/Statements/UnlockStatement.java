package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IExecutionStack;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ILockTable;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.IntegerValue;
import com.example.toylanguage_intellij.Model.Values.Value;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UnlockStatement implements IStatement {
    private final String variableName;

    public  UnlockStatement(String variableName) {
        this.variableName = variableName;
    }
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        ILockTable<Integer> lockTable = programState.getLockTable();

        if (symbolTable.isDefined(variableName)) {
            Value foundIndex = symbolTable.findValue(variableName);
            if (foundIndex.getType().equals(new IntegerType())) {
                IntegerValue foundIndexInt = (IntegerValue) foundIndex;
                if (lockTable.isDefined(foundIndexInt.getValue())) {
                    if (lockTable.findValue(foundIndexInt.getValue()).equals(programState.getId())) {
                        lockTable.update(foundIndexInt.getValue(), -1);
                    }
                }
                else {
                    throw new MyException("Lock does not exist");
                }
            }
            else {
                throw new MyException("The value associated with the variable from Lock is not of type int");
            }
        }
        else {
            throw new MyException("The variable in lock statement is not in symbol table.");
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
    public String toString() {
        return "unlock (" + this.variableName + ")";
    }
}
