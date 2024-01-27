package com.gui.toylanguage.model.expressions;


import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.types.IntType;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.IntValue;
import com.gui.toylanguage.model.values.Value;

public class ArithExp implements Exp{
    Exp e1;
    Exp e2;
    ArithOps op; //1 - plus, 2-minus 3-star, 4-divide

    public ArithExp(ArithOps op, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public  ArithExp(String op, Exp e1, Exp e2){
        switch (op){
            case "+":
                this.op = ArithOps.PLUS;
                break;
            case "-":
                this.op = ArithOps.MINUS;
                break;
            case "*":
                this.op = ArithOps.MULTIPLICATION;
                break;
            case "/":
                this.op = ArithOps.DIVISION;
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
        return new IntType();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapDictionary hpTbl) throws MyException {
        Value v1,v2;
        v1 = e1.eval(tbl, hpTbl);
        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(tbl, hpTbl);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                switch (op){
                    case PLUS:
                        return new IntValue(n1+n2);
                    case MINUS:
                        return new IntValue(n1-n2);
                    case MULTIPLICATION:
                        return new IntValue(n1*n2);
                    case DIVISION:
                        if(n2 == 0) throw new MyException("division by zero");
                        else return new IntValue(n1/n2);
                    default:
                        throw new MyException("Invaid operator");
                }
            }
            throw new MyException("second operand " + e2.toString() + " is not an integer");
        }
        else{
            throw new MyException("first operand " + e1.toString() + "is not an integer");
        }
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(op, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public String toString(){
        switch (op){
            case PLUS:
                return e1.toString() + " + " + e2.toString();
            case MINUS:
                return e1.toString() + " - " + e2.toString();
            case MULTIPLICATION:
                return e1.toString() + " * " + e2.toString();
            case DIVISION:
                return e1.toString() + " : " + e2.toString();
            default:
                return e1.toString() + " [invalid operator] " + e2.toString();
        }
    }
}
