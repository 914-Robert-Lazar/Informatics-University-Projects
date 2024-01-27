package com.gui.toylanguage.model.types;

import com.gui.toylanguage.model.values.BoolValue;
import com.gui.toylanguage.model.values.Value;

public class BoolType implements Type{
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BoolType)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
