package com.example.toylanguage_intellij.Model.Statements;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IExecutionStack;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.BooleanType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.BooleanValue;
import com.example.toylanguage_intellij.Model.Values.Value;

public class WhileStatement implements IStatement {
    Expression expression;
    IStatement statement;

    public WhileStatement(Expression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        Value expressionValuation = expression.evaluate(programState.getSymTable(), programState.getHeap());
        if (expressionValuation instanceof BooleanValue) {
            if (expressionValuation.equals(new BooleanValue(true))) {
                IExecutionStack<IStatement> executionStack = programState.getExecutionStack();
                executionStack.push(new WhileStatement(expression, statement));
                executionStack.push(statement);
            }
        }
        else {
            throw new MyException("Condition expression is not a boolean.");
        }

        return null;
    }
    
    @Override
    public String toString() {
        return "while (" + this.expression.toString() + ") " + this.statement + ";";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeExpression.equals(new BooleanType())) {
            statement.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else {
            throw new MyException("The condition of While does not have the type boolean.");
        }
    }
}
