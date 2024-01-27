package model.expressions;

import exception.InterpreterException;
import model.adt.DictionaryInterface;
import model.adt.HeapInterface;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class ReadHeapExpression implements Expression{
    private Expression expression;
    public ReadHeapExpression(Expression expression){
        this.expression=expression;
    }

    @Override
    public Value eval(DictionaryInterface<String, Value> table, HeapInterface<Integer,Value> heap) throws InterpreterException {
        Value value=expression.eval(table,heap);
        if (value instanceof ReferenceValue){
            return heap.get(((ReferenceValue) value).getAddress());
        }else {
            throw new InterpreterException("Value is not ReferenceValue");
        }
    }

    @Override
    public String toString() {
        return "ReadHeap("+expression+")";
    }

    @Override
    public Expression deepCopy() throws InterpreterException {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type type=expression.typeCheck(typeEnvironment);
        if (type instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInner();
        }else
            throw new InterpreterException("The argument is not of type ReferenceType");
    }
}
