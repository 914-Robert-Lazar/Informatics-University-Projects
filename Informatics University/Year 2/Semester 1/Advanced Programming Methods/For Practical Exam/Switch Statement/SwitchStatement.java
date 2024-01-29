package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.Expressions.RelationalExpression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IExecutionStack;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SwitchStatement implements IStatement {
    private Expression expression;
    private Expression expression1;
    private Expression expression2;
    private IStatement statement1;
    private IStatement statement2;
    private IStatement statement3;

    public SwitchStatement(Expression expression, Expression expression1, Expression expression2, IStatement statement1,
                           IStatement statement2, IStatement statement3) {
        this.expression = expression;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.statement1 = statement1;
        this.statement2 = statement2;
        this.statement3 = statement3;
    }
    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();

        IStatement newStatement = new IfStatement(new RelationalExpression(expression, expression1, "=="),
                statement1, new IfStatement(new RelationalExpression(expression, expression2, "=="),
                statement2, statement3));
        executionStack.push(newStatement);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        Type type1 = expression1.typecheck(typeEnv);
        Type type2 = expression2.typecheck(typeEnv);
        if (type.equals(type1) && type.equals(type2)) {
            statement1.typecheck(typeEnv.copy());
            statement2.typecheck(typeEnv.copy());
            statement3.typecheck(typeEnv.copy());
            return typeEnv;
        }
        else {
            throw new MyException("The expressions in switch case doesn't have the same type.");
        }
    }

    @Override
    public String toString() {
        return "switch(" + this.expression.toString() + ")" + "( case " + this.expression1.toString() + ": "
                + this.statement1.toString() + ") ( case " + this.expression2.toString() + ": " +
                this.statement2.toString() + ") default: " + this.statement3.toString() + ")";
    }
}
