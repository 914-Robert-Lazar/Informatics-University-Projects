package Model.Values;

import Model.Types.BooleanType;
import Model.Types.Type;

public class BooleanValue implements Value {
    boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString()
    {
        return "" + this.value;
    }

    @Override
    public Type getType() {
        return new BooleanType();
    }
    
}
