package com.example.toylanguage_intellij.Model.Expressions;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IHeap;
import com.example.toylanguage_intellij.Model.Types.ReferenceType;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.ReferenceValue;
import com.example.toylanguage_intellij.Model.Values.Value;

public class ReadFromHeapExpression implements Expression {
    Expression expression;

    public ReadFromHeapExpression(Expression expression) {
        this.expression = expression;
    }
    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        Value value = expression.evaluate(table, heap);
        if (!(value instanceof ReferenceValue)) {
            throw new MyException("The expression does not evaluate to a reference value.");
        }
        else {
            int address = ((ReferenceValue) value).getAddress();
            if (!heap.isDefined(address)) {
                throw new MyException("The address is not allocated on the heap");
            }
            else {
                return heap.findValue(address);
            }
        }
    }
    
    @Override
    public String toString() {
        return "ReadHeap(" + this.expression.toString() + ")";
    }
    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInner();
        }
        else {
            throw new MyException("The read from heap argument is not a ReferenceType.");
        }
    }
}
