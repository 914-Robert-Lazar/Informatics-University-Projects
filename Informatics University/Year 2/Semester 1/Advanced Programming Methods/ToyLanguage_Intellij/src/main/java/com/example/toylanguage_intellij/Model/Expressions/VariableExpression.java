package com.example.toylanguage_intellij.Model.Expressions;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IHeap;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.Value;

public class VariableExpression implements Expression {
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        return table.findValue(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.findValue(id);
    }
}
