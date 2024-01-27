package com.gui.toylanguage.model.statements;


import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.adt.MyIStack;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.expressions.Exp;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.Value;

public class AssignStmt implements IStmt{
    private final String id;
    private Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyHeapDictionary hpTbl = state.getHeapTable();

        if(symTbl.isDefined(id)){
            Value val = exp.eval(symTbl, hpTbl);
            Type typeId = (symTbl.lookUp(id)).getType();
            if(val.getType().equals(typeId)){
                symTbl.update(id, val);
            }
            else{
                throw new MyException("declared type of variable"+ id +" and type of the assigned expression do not match");
            }
        }
        else{
            throw new MyException("the used variable" + id + " was not declared before");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookUp(id);
        Type typeExp = exp.typecheck(typeEnv);
        if(typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(new String(id), exp.deepCopy());
    }

    @Override
    public String toString(){
        return id + " = " + exp.toString() +";";
    }
}
