package com.gui.toylanguage.model.statements;

import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.expressions.Exp;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.types.IntType;
import com.gui.toylanguage.model.types.StringType;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.IntValue;
import com.gui.toylanguage.model.values.StringValue;
import com.gui.toylanguage.model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{

    Exp exp;
    String var_name;

    public ReadFileStmt(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyHeapDictionary hpTbl = state.getHeapTable();
        MyIDictionary<String, BufferedReader> fileTbl = state.getFileTable();

        if(!symTbl.isDefined(var_name))
            throw new MyException("Variable not defined!");
        Value val = symTbl.lookUp(var_name);
        if(! val.getType().equals(new IntType())){
            throw new MyException("Variable type is not integer!");
        }
        Value val2 = exp.eval(symTbl, hpTbl);
        if(!val2.getType().equals(new StringType())){
            throw new MyException( "Provide filepath as a string");
        }
        StringValue filename = (StringValue) val2;
        if(!fileTbl.isDefined(filename.getVal())) {
            throw new MyException( "No such file is opened!");
        }
        BufferedReader bufreader = fileTbl.lookUp(filename.getVal());
        try {
            String line = bufreader.readLine();
            int fileval;
            if(line == null){
                fileval = 0;
            }
            else {
                fileval = Integer.parseInt(line);
            }
            symTbl.update(var_name, new IntValue(fileval));
        }catch(IOException e){
            throw new MyException(e.getMessage());
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookUp(var_name);
        Type typeExp = exp.typecheck(typeEnv);
        if(!typeExp.equals(new StringType())){
            throw new MyException("File path not provided as an integer");
        }
        if(!typeVar.equals(new IntType())){
            throw new MyException("Variable type is not integer!");
        }
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), new String(var_name));
    }

    @Override
    public String toString() {
        return "readFile(" + exp.toString() + ", " + var_name + ");";
    }
}
