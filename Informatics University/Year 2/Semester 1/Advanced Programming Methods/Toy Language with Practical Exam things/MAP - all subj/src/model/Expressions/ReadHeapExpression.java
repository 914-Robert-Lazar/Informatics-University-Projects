package model.Expressions;

import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Types.RefType;
import model.Types.Type;
import model.Values.RefValue;
import model.Values.Value;

public class ReadHeapExpression implements Expression{
    private final Expression expVarName;

    public ReadHeapExpression(Expression expression) {
        this.expVarName = expression;
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws InterpreterException {
        Value val = this.expVarName.eval(SymbolTable, Heap);
        if (!(val instanceof RefValue))
            throw new StatementException("Expression not of reference type!");

        RefValue refVal = (RefValue) val;
        int address = refVal.getAddress();
        if (!Heap.exists(address))
            throw new StatementException("Not allocated on heap");

        return Heap.get(address);
    }

    @Override
    public String toString() {
        return "readHeap(" + this.expVarName.toString() + ")";
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExpression(this.expVarName);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type type = this.expVarName.typecheck(table);
        if (type instanceof RefType) {
            RefType referenceType = (RefType) type;
            return referenceType.getInner();
        } else {
            throw new TypeException("Expression not of reference type");
        }
    }
}
