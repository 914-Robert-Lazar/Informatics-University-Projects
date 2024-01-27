package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class IfStatement implements Statement{
    private Expression expression;
    private Statement thenStatement;
    private Statement elseStatement;

    public IfStatement(Expression _ex,Statement then,Statement els){
        expression=_ex;
        thenStatement=then;
        elseStatement=els;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Value exp=expression.eval(state.getSymbolsTable(),state.getHeapTable());
        if (exp.getType().equals(new BoolType())){
            BoolValue exprEval=(BoolValue) exp;
            if (exprEval.getValue()){
                state.getExecutionStack().push(thenStatement);
            }
            else {
                state.getExecutionStack().push(elseStatement);
            }
        }
        return null;
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new IfStatement(expression,thenStatement.deepCopy(),elseStatement.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type expressionType=expression.typeCheck(typeEnvironment);
        if (expressionType.equals(new BoolType())){
            thenStatement.typeCheck(typeEnvironment.copy());
            elseStatement.typeCheck(typeEnvironment.copy());
            return typeEnvironment;
        }
        else
            throw new InterpreterException("If statement: The condition is not of type boolean");
    }

    @Override
    public String toString() {
        return "if " + expression + " then " + thenStatement + " else " +elseStatement;
    }
}
