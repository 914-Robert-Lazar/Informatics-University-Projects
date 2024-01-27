package Model.Statements;

import java.io.FileNotFoundException;
import java.io.IOException;

import Controller.MyException;
import Model.Expressions.Expression;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.IExecutionStack;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.BooleanType;
import Model.Types.Type;
import Model.Values.BooleanValue;
import Model.Values.Value;

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
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeExpression.equals(new BooleanType())) {
            statement.typecheck(typeEnv.copy());
            return typeEnv;
        }
        else {
            throw new MyException("The condition of While does not have the type boolean.");
        }
    }
}
