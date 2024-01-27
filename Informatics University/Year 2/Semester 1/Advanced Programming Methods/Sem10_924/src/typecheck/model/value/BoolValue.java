package typecheck.model.value;

import typecheck.model.type.BoolType;
import typecheck.model.type.Type;

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
