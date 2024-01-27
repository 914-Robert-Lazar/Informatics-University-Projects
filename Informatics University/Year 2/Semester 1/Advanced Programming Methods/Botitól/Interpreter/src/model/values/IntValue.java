package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value{
    private final int value;
    public IntValue(){
        this.value=0;
    }
    public IntValue(int value){
        this.value=value;
    }
    public int getValue(){
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntValue))
            return false;
        return ((IntValue) obj).getValue()==value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(value);
    }
}
