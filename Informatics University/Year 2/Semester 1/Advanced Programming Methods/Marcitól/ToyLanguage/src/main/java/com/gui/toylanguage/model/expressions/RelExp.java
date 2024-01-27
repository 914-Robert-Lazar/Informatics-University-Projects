package com.gui.toylanguage.model.expressions;

import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.types.BoolType;
import com.gui.toylanguage.model.types.IntType;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.BoolValue;
import com.gui.toylanguage.model.values.IntValue;
import com.gui.toylanguage.model.values.Value;
public class RelExp implements Exp{

    Exp e1;
    Exp e2;
    RelOps op;

    public RelExp(Exp e1, RelOps op, Exp e2){
        this.e1 = e1;
        this.op = op;
        this.e2 = e2;
    }

    public RelExp(Exp e1, String op, Exp e2){
        switch (op){
            case "==":
                this.op = RelOps.EQUAL;
                break;
            case "!=":
                this.op = RelOps.NOT_EQUAL;
                break;
            case "<":
                this.op = RelOps.SMALLER;
                break;
            case "<=":
                this.op = RelOps.LARGER_EQUAL;
                break;
            case ">":
                this.op = RelOps.LARGER;
                break;
            case ">=":
                this.op = RelOps.LARGER_EQUAL;
                break;
            default:
                this.op = RelOps.EQUAL;
                break;
        }
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if (!type1.equals(new IntType())){
            throw new MyException("First operand is not type integer!");
        }
        if(!type2.equals(new IntType())){
            throw new MyException("Second operand is not type integer!");
        }
        return new BoolType();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapDictionary hpTbl) throws MyException {
        Value v1 = e1.eval(tbl, hpTbl);
        if(!v1.getType().equals(new IntType())){
            throw new MyException("First operand is not type integer");
        }
        Value v2 = e2.eval(tbl, hpTbl);
        if(!v2.getType().equals(new IntType())){
            throw new MyException("Second operand is not type integer");
        }
        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int n1 = i1.getVal();
        int n2 = i2.getVal();
        switch (op){
            case EQUAL -> {
                return new BoolValue(n1 == n2);
            }
            case NOT_EQUAL ->{
                return new BoolValue(n1 != n2);
            }
            case SMALLER -> {
                return new BoolValue(n1 < n2);
            }
            case SMALLER_EQUAL -> {
                return new BoolValue(n1 <= n2);
            }
            case LARGER -> {
                return new BoolValue(n1 > n2);
            }
            case LARGER_EQUAL -> {
                return new BoolValue(n1 >= n2);
            }
            default -> {
                return new BoolValue(false);
            }
        }
    }
    @Override
    public String toString(){
        switch (op){
            case EQUAL:
                return e1.toString() + " == " + e2.toString();
            case NOT_EQUAL:
                return e1.toString() + " != " + e2.toString();
            case SMALLER:
                return e1.toString() + " < " + e2.toString();
            case SMALLER_EQUAL:
                return e1.toString() + " <=" + e2.toString();
            case LARGER:
                return e1.toString() + " > " + e2.toString();
            case LARGER_EQUAL:
                return e1.toString() + " >= " + e2.toString();
            default:
                return e1.toString() + " [invalid operator] " + e2.toString();
        }
    }
    @Override
    public Exp deepCopy() {
        return new RelExp(e1.deepCopy(), op, e2.deepCopy());
    }
}
