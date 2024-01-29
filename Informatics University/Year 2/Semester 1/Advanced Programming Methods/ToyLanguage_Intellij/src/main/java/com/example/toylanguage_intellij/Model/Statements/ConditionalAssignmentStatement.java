package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IExecutionStack;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.BooleanType;
import com.example.toylanguage_intellij.Model.Types.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ConditionalAssignmentStatement implements IStatement{
    String id;
    Expression expression1;
    Expression expression2;
    Expression expression3;

    public ConditionalAssignmentStatement(String id, Expression expression1, Expression expression2, Expression expression3) {
        this.id = id;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();

        IStatement newStatement = new IfStatement(expression1, new AssignmentStatement(id, expression2),
                new AssignmentStatement(id, expression3));

        executionStack.push(newStatement);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type variableType = typeEnv.findValue(id);
        Type exprType1 = expression1.typecheck(typeEnv);
        Type exprType2 = expression2.typecheck(typeEnv);
        Type exprType3 = expression3.typecheck(typeEnv);
        if (exprType1.equals(new BooleanType())) {
            if (variableType.equals(exprType2) && variableType.equals(exprType3)) {
                return typeEnv;
            }
            else {
                throw new MyException("The variable and the given expressions don't have the same type in conditional assignment.");
            }
        }
        else {
            throw new MyException("The condition does not evaluate to type bool in conditional assignment.");
        }
    }

    @Override
    public String toString(){
        return this.id + "=(" + this.expression1 + ")?" + this.expression2 + ":" + this.expression3;
    }
}
