package com.example.toylanguage_intellij.Model.Expressions;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IHeap;
import com.example.toylanguage_intellij.Model.Types.Type;
import com.example.toylanguage_intellij.Model.Values.Value;

public interface Expression {
    Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException;
    Type typecheck(IDictionary<String, Type> typeEnv) throws MyException;
}
