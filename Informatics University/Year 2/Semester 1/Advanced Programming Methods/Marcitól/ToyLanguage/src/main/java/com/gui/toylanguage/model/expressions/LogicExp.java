package com.gui.toylanguage.model.expressions;


import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.types.BoolType;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.BoolValue;
import com.gui.toylanguage.model.values.Value;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    LogicOps op; //0 - &&, 1 - ||

    public LogicExp(Exp e1, Exp e2, LogicOps op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if (!type1.equals(new BoolType())){
            throw new MyException("First operand is not type integer!");
        }
        if(!type2.equals(new BoolType())){
            throw new MyException("Second operand is not type integer!");
        }
        return new BoolType();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapDictionary hpTbl) throws MyException {
        Value v1,v2;
        v1 = e1.eval(tbl, hpTbl);
        if(v1.getType().equals(new BoolType())){
            v2 = e2.eval(tbl, hpTbl);
            if(v2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                boolean b1 = i1.getVal();
                boolean b2 = i2.getVal();
                switch (op) {
                    case AND:
                        return new BoolValue(b1 && b2);
                    case OR:
                        return new BoolValue(b1 || b2);
                    default:
                        throw new MyException("Invalid logical operator");
                }
            }
            else{
                throw  new MyException("Second operand is not a boolean");
            }
        }
        else {
            throw new MyException("First operand is not a boolean");
        }
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public String toString(){
        switch (op){
            case AND:
                return e1.toString() + " && " + e2.toString();
            case OR:
                return e1.toString() + " || " + e2.toString();
            default:
                return e1.toString() + " [invalid operator] " + e2.toString();
        }
    }
}
