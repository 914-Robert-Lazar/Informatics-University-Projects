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

public class WhileStmt implements IStmt{

    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt){
        this.exp = exp;
        this.stmt = stmt;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyHeapDictionary hpTbl = state.getHeapTable();
        Value v = exp.eval(symTbl, hpTbl);
        if(!v.getType().equals(new BoolType())){
            throw new MyException("Expression " + exp.toString() + " is not evaluated to BoolType in while statement!");
        }
        BoolValue bv = (BoolValue) v;
        if(bv.getVal()){
            exeStack.push(deepCopy());
            exeStack.push(stmt.deepCopy());
        }


        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if(!typeExp.equals(new BoolType())){
            throw new MyException("The condition of WHILE has not the type bool");
        }
        MyIDictionary<String, Type> newTypeEnv = new MyDictionary<String, Type>();
        newTypeEnv.setDictionary((HashMap<String, Type>) typeEnv.getDictionary().clone());
        stmt.typecheck(newTypeEnv);

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString(){
        return "WHILE ( " + exp.toString() + " ) { " + stmt.toString() + " };";
    }
}
