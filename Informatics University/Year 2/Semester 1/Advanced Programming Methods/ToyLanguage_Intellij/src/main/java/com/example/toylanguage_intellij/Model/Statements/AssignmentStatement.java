package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IHeap;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.Value;

public class AssignmentStatement implements IStatement{
    String id;
    Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        IDictionary<String, Value> symbolTable = programState.getSymTable();
        IHeap<Value> heap = programState.getHeap();

        if (symbolTable.isDefined(id)) {
            Value rightSide = this.expression.evaluate(symbolTable, heap);

            Type idType = (symbolTable.findValue(id)).getType();
            if (idType.equals(rightSide.getType())) {
                symbolTable.put(id, rightSide);
            }
            else {
                throw new MyException("The declared type for variable " + id + "and the type of assigned expression do not match.");
            }
        }
        else {
            throw new MyException("The used variable " + id + " was not declared yet.");
        }

        return null;
    }

    @Override
    public String toString() {
        return id + " = " + expression.toString();
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVariable = typeEnv.findValue(id);
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeVariable.equals(typeExpression)) {
            return typeEnv;
        }
        else {
            throw new MyException("Assignment: right hand side and left hand side have different types.");
        }
    }
    
}
