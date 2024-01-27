package typecheck.model.exp;

import typecheck.model.adt.MyException;
import typecheck.model.adt.MyIDictionary;
import typecheck.model.adt.MyIDictionaryHeap;
import typecheck.model.type.Type;
import typecheck.model.value.Value;

public class VarExp implements Expression{
    private final String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> tbl, MyIDictionaryHeap<Value> heap) throws MyException {
        return tbl.lookup(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }


    @Override
    public String toString() {
        return id;
    }

}