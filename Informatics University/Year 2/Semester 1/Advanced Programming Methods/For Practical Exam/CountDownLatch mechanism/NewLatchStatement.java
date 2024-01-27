package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.*;
import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.IntegerValue;
import com.example.toylanguage_intellij.Model.Values.Value;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NewLatchStatement implements IStatement{
    String variableName;
    Expression expression;
    public NewLatchStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        ILatchTable<Integer> latchTable = programState.getLatchTable();
        IHeap<Value> heap = programState.getHeap();

        Value exprValue = expression.evaluate(symbolTable, heap);
        if (!exprValue.getType().equals(new IntegerType())) {
            throw new MyException("Value of given expression is not int in New Latch Statement");
        }

        IntegerValue exprValueInt = (IntegerValue) exprValue;

        if (symbolTable.isDefined(variableName)) {
            Value value = symbolTable.findValue(variableName);
            if (value.getType().equals(new IntegerType())) {
                int newFreeLocation = latchTable.put(exprValueInt.getValue());
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
        Type exprType = expression.typecheck(typeEnv);
        if(varType.equals(new IntegerType()) && exprType.equals(new IntegerType())){
            return typeEnv;
        } else throw new MyException("Variable or expression not of type int in new latch statement");
    }

    @Override
    public String toString(){
        return "newLatch(" + this.variableName + ", " + this.expression.toString() + ")";
    }
}
