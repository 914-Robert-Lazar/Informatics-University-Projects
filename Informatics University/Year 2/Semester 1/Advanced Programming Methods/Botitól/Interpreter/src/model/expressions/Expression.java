package model.expressions;

import exception.InterpreterException;
import model.adt.DictionaryInterface;
import model.adt.HeapInterface;
import model.types.Type;
import model.values.Value;

public interface Expression {
    Value eval(DictionaryInterface<String,Value> table, HeapInterface<Integer,Value> heap) throws InterpreterException;
    Expression deepCopy() throws InterpreterException;
    Type typeCheck(DictionaryInterface<String,Type> typeEnvironment) throws InterpreterException;
}
