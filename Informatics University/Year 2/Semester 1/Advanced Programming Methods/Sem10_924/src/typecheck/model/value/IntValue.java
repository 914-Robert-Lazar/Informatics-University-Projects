package typecheck.model.value;

import typecheck.model.type.IntType;
import typecheck.model.type.Type;

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
