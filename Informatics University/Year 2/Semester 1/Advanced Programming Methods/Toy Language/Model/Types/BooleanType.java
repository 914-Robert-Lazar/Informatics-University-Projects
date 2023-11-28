package Model.Types;

import Model.Values.BooleanValue;
import Model.Values.Value;

public class BooleanType implements Type{
    @Override
    public boolean equals(Object other) {
        return other instanceof BooleanType;
    }

    @Override
    public String toString() {
        return "boolean";
    }

    @Override
    public Value defaultValue() {
        return new BooleanValue(false);
    }
}
