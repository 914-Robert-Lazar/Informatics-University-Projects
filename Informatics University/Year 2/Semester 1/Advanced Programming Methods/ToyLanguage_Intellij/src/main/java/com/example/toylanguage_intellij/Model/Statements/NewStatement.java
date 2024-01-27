package com.example.toylanguage_intellij.Model.Statements;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IHeap;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.ReferenceType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.ReferenceValue;
import com.example.toylanguage_intellij.Model.Values.Value;

public class NewStatement implements IStatement {
    String name;
    Expression expression;

    public NewStatement(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    } 
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        IHeap<Value> heap = programState.getHeap();
        if (!symbolTable.isDefined(name)) {
            throw new MyException("The variable is not yet defined.");
        }
        else  {
            Value refValue = symbolTable.findValue(name);
            if (!(refValue.getType() instanceof ReferenceType)) {
                throw new MyException("The type of the variable is not a reference type.");
            }
            else {
                Value value = expression.evaluate(programState.getSymTable(), programState.getHeap());
                if (!value.getType().equals(((ReferenceValue) refValue).getLocationType())) {
                    throw new MyException("The type of the expression's result and the location type of the varialbe are different.");
                }
                else {
                    int currentAddress = heap.put(value);
                    symbolTable.put(name, new ReferenceValue(currentAddress, value.getType()));
                }
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return "new(" + this.name + ", " + this.expression.toString() + ")";
    }
    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVariable = typeEnv.findValue(name);
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeVariable.equals(new ReferenceType(typeExpression))) {
            return typeEnv;
        }
        else {
            throw new MyException("NEW statement: right hand side and left hand side have different types.");
        }
    }
}
