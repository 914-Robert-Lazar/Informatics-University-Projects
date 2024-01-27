package model.Types;
import model.Values.IntValue;
import model.Values.Value;

public class IntType implements Type{
    // public IntType() {}

    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value getDefault() {
        return new IntValue(0);
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }


}
