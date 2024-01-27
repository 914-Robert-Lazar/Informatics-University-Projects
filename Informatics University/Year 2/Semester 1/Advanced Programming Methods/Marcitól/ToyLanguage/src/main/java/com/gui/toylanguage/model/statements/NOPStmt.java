package com.gui.toylanguage.model.statements;


import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.types.Type;

public class NOPStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NOPStmt();
    }

    @Override
    public String toString(){
        return "NOP";
    }
}
