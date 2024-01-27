package typecheck.model.exp;

import typecheck.model.adt.MyException;
import typecheck.model.adt.MyIDictionary;
import typecheck.model.adt.MyIDictionaryHeap;
import typecheck.model.type.Type;
import typecheck.model.value.Value;

public class ValueExp implements Expression{
    private Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> tbl, MyIDictionaryHeap<Value> heap) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}
