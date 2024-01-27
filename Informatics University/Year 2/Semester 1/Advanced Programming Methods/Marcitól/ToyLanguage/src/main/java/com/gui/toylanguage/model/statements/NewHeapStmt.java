package com.gui.toylanguage.model.statements;


import com.gui.toylanguage.adt.MyHeapDictionary;
import com.gui.toylanguage.adt.MyIDictionary;
import com.gui.toylanguage.exceptions.MyException;
import com.gui.toylanguage.model.expressions.Exp;
import com.gui.toylanguage.model.programState.PrgState;
import com.gui.toylanguage.model.types.RefType;
import com.gui.toylanguage.model.types.Type;
import com.gui.toylanguage.model.values.RefValue;
import com.gui.toylanguage.model.values.Value;

public class NewHeapStmt implements IStmt{

    private String id;
    private Exp exp;

    public NewHeapStmt(String id, Exp exp){
        this.id = id;
        this.exp = exp;
    }
    @Override
    public synchronized PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyHeapDictionary heapTable = state.getHeapTable();

        Value v1 = symTable.lookUp(id); //throws exception if not found
        if(! (v1.getType() instanceof RefType)){
            throw new MyException("Variable " + id +" is not type Reference!");
        }

        RefValue ref1 = (RefValue) v1;
        Type locationType = ref1.getLocationType();
        Value evaluated = exp.eval(symTable, heapTable);
        if (!(locationType.equals(evaluated.getType()))){
            throw new MyException("NewStatement error: Expression " + evaluated.toString() + " is not the same type as the variable " + id + " points to!");
        }

        int address = state.getHeapTable().put(evaluated);
        symTable.update(id, new RefValue(address, locationType)); //update its address to the new one

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookUp(id);
        Type typeExp = exp.typecheck(typeEnv);
        if(!typeVar.equals(new RefType(typeExp))){
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
        }
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NewHeapStmt(id, exp);
    }

    @Override
    public String toString(){
        return "new ( " + id + ", " + exp.toString() + " );";
    }
}
