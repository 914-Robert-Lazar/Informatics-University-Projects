package model.expressions;

import exception.InterpreterException;
import model.adt.DictionaryInterface;
import model.adt.HeapInterface;
import model.types.Type;
import model.values.Value;

public class VariableExpression implements Expression{
    String id;

    public VariableExpression(String _id){
        id=_id;
    }
    @Override
    public Value eval(DictionaryInterface<String, Value> table, HeapInterface<Integer,Value> heap) throws InterpreterException {
        return table.lookup(id);
    }

    @Override
    public Expression deepCopy() throws InterpreterException {
        return new VariableExpression(id);
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        return typeEnvironment.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
