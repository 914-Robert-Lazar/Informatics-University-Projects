package model.values;

import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value{

    private final boolean value;

    public BoolValue(){
        value=false;
    }
    public BoolValue(boolean _value){
        value=_value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BoolValue))
            return false;
        return ((BoolValue) obj).getValue()==value;
    }

    public boolean getValue(){
        return value;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(value);
    }
}
