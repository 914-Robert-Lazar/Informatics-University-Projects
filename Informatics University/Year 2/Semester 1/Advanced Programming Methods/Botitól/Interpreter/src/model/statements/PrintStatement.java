package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.types.Type;

public class PrintStatement implements Statement{
    private Expression expression;
    public PrintStatement(Expression _exp){
        expression=_exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        state.getOut().add(expression.eval(state.getSymbolsTable(), state.getHeapTable()));
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(expression);
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "Print(" + expression + ")"+";";
    }
}
