package com.gui.toylanguage.model.statements;

import com.gui.toylanguage.adt.MyIList;
import com.gui.toylanguage.model.expressions.Exp;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.values.Value;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString(){
        return "print("+exp.toString()+");";
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> out = state.getOut();
        out.add(exp.eval(state.getSymTable(), state.getHeapTable()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }
}
