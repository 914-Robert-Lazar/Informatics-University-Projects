package textmenu.model.exp;

import textmenu.model.adt.MyException;
import textmenu.model.adt.MyIDictionary;
import textmenu.model.adt.MyIDictionaryHeap;
import textmenu.model.value.Value;

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
    public String toString() {
        return id;
    }

}
