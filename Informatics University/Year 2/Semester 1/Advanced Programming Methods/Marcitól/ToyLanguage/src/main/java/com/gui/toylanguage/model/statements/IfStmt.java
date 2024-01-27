package com.gui.toylanguage.model.statements;


import com.gui.toylanguage.adt.MyDictionary;
import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.adt.MyIStack;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.expressions.Exp;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.types.BoolType;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.BoolValue;
import com.gui.toylanguage.model.values.Value;

import java.util.HashMap;


public class IfStmt implements IStmt{
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el){
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public String toString(){
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString()+")ELSE("+elseS.toString()+");)";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyHeapDictionary hpTbl = state.getHeapTable();
        Value v = exp.eval(symTbl, hpTbl);
        if(v.getType().equals(new BoolType())){
            BoolValue bv = (BoolValue) v;
            boolean b = bv.getVal();
            if(b){
                stk.push(thenS);
            }
            else{
                stk.push(elseS);
            }
        }
        else{
            throw new MyException("Expression is not BoolValue");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if(!typeExp.equals(new BoolType())){
            throw new MyException("The condition of IF has not the type bool");
        }
        MyIDictionary<String, Type> newTypeEnv1 = new MyDictionary<String, Type>();
        newTypeEnv1.setDictionary((HashMap<String, Type>) typeEnv.getDictionary().clone());
        thenS.typecheck(newTypeEnv1); //clone(typeEnv)
        MyIDictionary<String, Type> newTypeEnv2 = new MyDictionary<String, Type>();
        newTypeEnv2.setDictionary((HashMap<String, Type>) typeEnv.getDictionary().clone());
        elseS.typecheck(newTypeEnv2); //clone(typeEnv)
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }
}
