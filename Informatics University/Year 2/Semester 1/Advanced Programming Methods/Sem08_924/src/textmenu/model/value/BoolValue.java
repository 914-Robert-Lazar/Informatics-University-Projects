package textmenu.model.value;

import textmenu.model.type.BoolType;
import textmenu.model.type.IntType;
import textmenu.model.type.Type;

public class BoolValue implements Value {
    boolean val;

    public BoolValue(boolean v){
        val=v;
    }

    @Override
    public String toString() {
        return ""+ val;
    }
    public Type getType() { return new BoolType();}
}

