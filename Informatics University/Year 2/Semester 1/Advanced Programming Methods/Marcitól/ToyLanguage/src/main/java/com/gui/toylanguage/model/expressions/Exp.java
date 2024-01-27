package com.gui.toylanguage.model.expressions;


import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.Value;

public interface Exp {

    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    Value eval(MyIDictionary<String, Value> tbl, MyHeapDictionary heaptbl) throws MyException;

    Exp deepCopy();
}
