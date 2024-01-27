package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.*;
import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.IntegerValue;
import com.example.toylanguage_intellij.Model.Values.Value;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CreateSemaphoreStatement implements IStatement{
    String variableName;
    Expression expression;
    public CreateSemaphoreStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        ISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable = programState.getSemaphoreTable();
        IHeap<Value> heap = programState.getHeap();

        Value exprValue = expression.evaluate(symbolTable, heap);
        if (!exprValue.getType().equals(new IntegerType())) {
            throw new MyException("Value of given expression is not int in Create Semaphore Statement");
        }

        IntegerValue exprValueInt = (IntegerValue) exprValue;

        if (symbolTable.isDefined(variableName)) {
            Value value = symbolTable.findValue(variableName);
            if (value.getType().equals(new IntegerType())) {
                int newFreeLocation = semaphoreTable.put(new Pair<>(exprValueInt.getValue(), new LinkedList<>()));
                symbolTable.put(variableName, new IntegerValue(newFreeLocation));
            }
            else {
                throw new MyException("The value associated with the variable from create semaphore is not of type int");
            }
        }
        else {
            throw new MyException("The variable in create semaphore statement is not in symbol table.");
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.findValue(variableName);
        Type exprType = expression.typecheck(typeEnv);
        if(varType.equals(new IntegerType()) && exprType.equals(new IntegerType())){
            return typeEnv;
        } else throw new MyException("Variable or expression not of type int in create semaphore statement");
    }

    @Override
    public String toString(){
        return "createSemaphore(" + this.variableName + ", " + this.expression.toString() + ")";
    }
}
