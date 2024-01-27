package model.expressions;

import exception.InterpreterException;
import model.adt.DictionaryInterface;
import model.adt.HeapInterface;
import model.types.Type;
import model.values.Value;

public class ValueExpression implements Expression{
    private Value value;

    public ValueExpression(Value _value){
        this.value=_value;
    }

    @Override
    public Value eval(DictionaryInterface<String, Value> table, HeapInterface<Integer,Value> heap) throws InterpreterException {
        return value;
    }

    @Override
    public Expression deepCopy() throws InterpreterException {
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
