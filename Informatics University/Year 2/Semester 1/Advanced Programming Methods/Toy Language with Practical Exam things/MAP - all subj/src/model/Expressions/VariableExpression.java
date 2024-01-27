package model.Expressions;

import exceptions.InterpreterException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Types.Type;
import model.Values.Value;

public class VariableExpression implements Expression{
    private final String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return this.id;
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws InterpreterException {
        return SymbolTable.lookup(this.id);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(this.id);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        return table.lookup(id);
    }
}
