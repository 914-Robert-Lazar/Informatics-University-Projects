package com.example.toylanguage_intellij.Model.Expressions;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IHeap;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.Value;

public class ValueExpression implements Expression{
    Value e;

    public ValueExpression(Value e) {
        this.e = e;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        return e;
    }
    
    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}
