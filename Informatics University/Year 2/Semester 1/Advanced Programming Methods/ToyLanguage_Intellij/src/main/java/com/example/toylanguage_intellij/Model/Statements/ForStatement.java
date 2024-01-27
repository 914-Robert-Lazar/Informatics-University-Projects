package com.example.toylanguage_intellij.Model.Statements;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.Expressions.Expression;
import com.example.toylanguage_intellij.Model.Expressions.RelationalExpression;
import com.example.toylanguage_intellij.Model.Expressions.VariableExpression;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IExecutionStack;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ForStatement implements IStatement{
    Expression initializeExpression;
    Expression conditionExpression;
    Expression afterExpression;
    IStatement statement;

    public ForStatement(Expression initializeExpression, Expression conditionExpression, Expression afterExpression, IStatement statement) {
        this.initializeExpression = initializeExpression;
        this.conditionExpression = conditionExpression;
        this.afterExpression = afterExpression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();

        IStatement newStatement = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
                                    new CompoundStatement(new AssignmentStatement("v", initializeExpression),
                                            new WhileStatement(new RelationalExpression(new VariableExpression("v"),
                                                    conditionExpression, "<"), new CompoundStatement(statement,
                                                    new AssignmentStatement("v", afterExpression)))));

        executionStack.push(newStatement);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        typeEnv = new VariableDeclarationStatement("v", new IntegerType()).typecheck(typeEnv);
        Type vType = typeEnv.findValue("v");
        if (!(vType.equals(new IntegerType()))) {
            throw new MyException("v in for statement does not have integer type");
        }
        Type initExprType = this.initializeExpression.typecheck(typeEnv);
        Type condExprType = this.conditionExpression.typecheck(typeEnv);
        Type afterExprType = this.afterExpression.typecheck(typeEnv);
        if (initExprType.equals(new IntegerType())) {
            if (condExprType.equals(new IntegerType())) {
                if (afterExprType.equals(new IntegerType())) {
                    return typeEnv;
                }
                else {
                    throw new MyException("The after expression is not an int.");
                }
            }
            else {
                throw new MyException("The condition expression is not an int.");
            }
        }
        else {
            throw new MyException("The initialized expression is not an int.");
        }
    }

    @Override
    public String toString() {
        return "for(v = " + initializeExpression.toString() +
                "; v < " + conditionExpression.toString() +
                "; v = " + afterExpression.toString() +
                ") {" + statement.toString() + " }";
    }
}
