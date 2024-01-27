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

public class WriteHeapStmt implements IStmt{

    private String id;
    private Exp exp;

    public WriteHeapStmt(String id, Exp exp){
        this.id = id;
        this.exp = exp;
    }
    @Override
    public synchronized PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyHeapDictionary heapTable = state.getHeapTable();

        Value v1 = symTable.lookUp(id); // throws exception already!
        if(!(v1.getType() instanceof RefType))
            throw new MyException("Variable " + id + "is not type Ref");

        RefValue refV = (RefValue) v1;
        if(!heapTable.isDefined(refV.getAddress()))
            throw new MyException("Key " + refV.getAddress() + " is not defined in heap table");

        Type locationType = refV.getLocationType();
        Value evaluated = exp.eval(symTable, heapTable);
        if(!locationType.equals(evaluated.getType())){
            throw new MyException("Expression " + evaluated.toString() + " is not the same type as the variable " + id + " points to!");
        }

        heapTable.update(refV.getAddress(), evaluated);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookUp(id);
        Type typeExp = exp.typecheck(typeEnv);
        if(!typeVar.equals(new RefType(typeExp))){
            throw new MyException("WRITEHEAP stmt: right hand side and left hand side have different types ");
        }
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(id, exp.deepCopy());
    }
    @Override
    public String toString(){
        return "writeHeap( " + id + ", " + exp.toString() + " );";
    }
}
