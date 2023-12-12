package Model.Statements;

import Controller.MyException;
import Model.Expressions.Expression;
import Model.ProgramStateComponents.IExecutionStack;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.BooleanType;
import Model.Values.BooleanValue;
import Model.Values.Value;

public class IfStatement implements IStatement{
    IStatement ifStatement, elseStatement;
    Expression condition;

    public IfStatement(Expression condition, IStatement ifStatement, IStatement elseStatement) {
        this.condition = condition;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }


    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();
        Value value = condition.evaluate(programState.getSymTable(), programState.getHeap());
        if (value.getType() instanceof BooleanType) {
            BooleanValue booleanValue = (BooleanValue) value;
            if (booleanValue.getValue() == true) {
                executionStack.push(ifStatement);
            }
            else {
                executionStack.push(elseStatement);
            }
        }
        else {
            throw new MyException("The condition does not evaluate to a boolean type.");
        }

        return null;
    }
    
    @Override
    public String toString() {
        return "If (" + condition.toString() + ") then (" + ifStatement.toString() + ") else (" + elseStatement + ")";
    }
}
