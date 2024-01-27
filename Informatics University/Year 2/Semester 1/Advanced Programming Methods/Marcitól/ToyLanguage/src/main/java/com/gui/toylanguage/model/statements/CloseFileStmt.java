package com.gui.toylanguage.model.statements;


import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.expressions.Exp;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.types.StringType;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.StringValue;
import com.gui.toylanguage.model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStmt implements IStmt{

    Exp exp;

    public CloseFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, BufferedReader> fileTbl = state.getFileTable();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyHeapDictionary hpTbl = state.getHeapTable();
        Value v = exp.eval(symTbl, hpTbl);
        if(v.getType().equals(new StringType()))
        {
            StringValue filename = (StringValue) v;
            if(fileTbl.isDefined(filename.getVal()))
            {
                BufferedReader bufreader = fileTbl.lookUp(filename.getVal());
                fileTbl.remove(filename.getVal());
                try {
                    bufreader.close();
                }catch(IOException e){
                    throw new MyException(e.getMessage());
                }
            }
            else {
                throw new MyException("File is not opened!");
            }
        }
        else {
            throw new MyException("Provide file as a string");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseFileStmt(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "close(" + exp.toString() + ");";
    }
}
