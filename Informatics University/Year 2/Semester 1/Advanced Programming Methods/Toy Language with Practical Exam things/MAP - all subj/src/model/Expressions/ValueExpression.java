package model.Expressions;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Types.Type;
import model.Values.Value;

public class ValueExpression implements Expression{
    private final Value expression;

    public ValueExpression(Value expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> symbolTable, MyHeapInterface<Value> Heap) throws ExpressionException {
        return expression;
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(this.expression);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        return this.expression.getType();
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
