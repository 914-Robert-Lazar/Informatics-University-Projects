package com.gui.toylanguage.model.statements;


import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.Value;

public class VarDeclStmt implements IStmt{

    String name;
    Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(name))
            throw new MyException("Variable already declared");
        else{
            symTable.add(name, type.defaultValue());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(name, type);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(new String(name), type.deepCopy());
    }

    @Override public String toString(){
        return type.toString() + " " + name + ";";
    }
}
