package com.gui.toylanguage.model.expressions;


import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.Value;

public class ValueExp implements Exp{
    Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapDictionary hpTbl) throws MyException {
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }

    @Override
    public String toString(){
        return String.valueOf(e);
    }
}
