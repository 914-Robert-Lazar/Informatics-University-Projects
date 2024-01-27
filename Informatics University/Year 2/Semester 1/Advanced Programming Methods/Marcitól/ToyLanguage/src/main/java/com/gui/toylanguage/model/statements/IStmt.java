package com.gui.toylanguage.model.statements;

import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.types.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;

    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;

    IStmt deepCopy();
}
