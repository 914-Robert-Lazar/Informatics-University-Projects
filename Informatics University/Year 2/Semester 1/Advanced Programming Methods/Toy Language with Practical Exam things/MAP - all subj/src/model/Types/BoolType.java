package model.Types;

import model.Values.BoolValue;
import model.Values.Value;

public class BoolType implements Type {
    //public BoolType() {}

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value getDefault() {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }
}
