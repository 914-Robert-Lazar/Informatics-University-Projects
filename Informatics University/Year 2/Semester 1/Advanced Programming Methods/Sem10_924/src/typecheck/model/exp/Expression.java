package typecheck.model.exp;

import typecheck.model.adt.MyException;
import typecheck.model.adt.MyIDictionary;
import typecheck.model.adt.MyIDictionaryHeap;
import typecheck.model.type.Type;
import typecheck.model.value.Value;

public interface Expression {
    Value evaluate(MyIDictionary<String,Value> tbl, MyIDictionaryHeap<Value> heap) throws MyException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}