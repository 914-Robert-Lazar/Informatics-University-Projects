package textmenu.model.exp;

import textmenu.model.adt.MyException;
import textmenu.model.adt.MyIDictionary;
import textmenu.model.adt.MyIDictionaryHeap;
import textmenu.model.value.Value;

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
}