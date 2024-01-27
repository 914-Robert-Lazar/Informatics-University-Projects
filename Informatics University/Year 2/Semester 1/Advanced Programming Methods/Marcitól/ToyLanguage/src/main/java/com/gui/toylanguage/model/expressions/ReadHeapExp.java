package com.gui.toylanguage.model.expressions;


import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.types.RefType;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.RefValue;
import com.gui.toylanguage.model.values.Value;

public class ReadHeapExp implements Exp{

    Exp exp;
    public ReadHeapExp(Exp exp){
        this.exp = exp;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        if(type instanceof RefType){
            RefType refType = (RefType) type;
            return refType.getInnerType();
        }
        throw new MyException("The rH argument is not a RefType");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapDictionary heaptbl) throws MyException {
        Value v = exp.eval(tbl, heaptbl);
        if(!(v instanceof RefValue)){
            throw new MyException("Exp " + exp.toString() + " is not type of reference!");
        }
        RefValue rv = (RefValue) v;
        int address = rv.getAddress();
        if(!heaptbl.isDefined(address)){
            throw new MyException("Address " + address + " not defined in the heapTable!");
        }
        return heaptbl.lookUp(address);
    }

    @Override
    public String toString(){
        return "rH( " + exp.toString() + " );";
    }
    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }
}
