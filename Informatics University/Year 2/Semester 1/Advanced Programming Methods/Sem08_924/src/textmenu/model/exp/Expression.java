package textmenu.model.exp;

import textmenu.model.adt.MyException;
import textmenu.model.adt.MyIDictionary;
import textmenu.model.adt.MyIDictionaryHeap;
import textmenu.model.value.Value;

public interface Expression {
    Value evaluate(MyIDictionary<String,Value> tbl, MyIDictionaryHeap<Value> heap) throws MyException;
}
