package com.gui.toylanguage.model.expressions;

import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.Value;

public class VarExp implements Exp{
    String id;
    public VarExp(String id){
        this.id = id;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookUp(id);
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapDictionary hpTbl) throws MyException {
        return tbl.lookUp(id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(new String(id));
    }

    @Override
    public String toString(){
        return id;
    }
}
