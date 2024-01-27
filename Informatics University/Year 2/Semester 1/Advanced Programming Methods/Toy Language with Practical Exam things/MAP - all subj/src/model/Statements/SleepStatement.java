package model.Statements;

import exceptions.InterpreterException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;
import model.Expressions.ArithmeticExpression;
import model.Expressions.Expression;
import model.Expressions.ValueExpression;
import model.ProgramState;
import model.Types.IntType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.Value;

public class SleepStatement implements StatementInterface{
    private final Expression exp;

    public SleepStatement(Expression exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyStackInterface<StatementInterface> stack = state.getExeStack();
        Value value = this.exp.eval(state.getSymbolTable(), state.getHeap());
        if (!value.equals(new IntValue(0))) {
            stack.push(new SleepStatement(new ArithmeticExpression('-', this.exp, new ValueExpression(new IntValue(1)))));
        }
        state.setExeStack(stack);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new SleepStatement(this.exp);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type expressionType = this.exp.typecheck(table);
        if (expressionType.equals(new IntType())) {
            return table;
        }
        throw new TypeException("Sleep must have an integer argument");
    }

    @Override
    public String toString() {
        return "sleep (" + this.exp.toString() + ")";
    }
}
