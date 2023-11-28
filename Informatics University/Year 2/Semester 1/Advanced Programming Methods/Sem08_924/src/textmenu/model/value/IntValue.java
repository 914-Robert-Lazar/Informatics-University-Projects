package textmenu.model.value;

import textmenu.model.type.IntType;
import textmenu.model.type.RefType;
import textmenu.model.type.Type;

public class IntValue implements Value {
    int val;

    public IntValue(int v){
        val=v;
    }

    @Override
    public String toString() {
        return ""+val;
    }
    public Type getType() { return new IntType();}
}

