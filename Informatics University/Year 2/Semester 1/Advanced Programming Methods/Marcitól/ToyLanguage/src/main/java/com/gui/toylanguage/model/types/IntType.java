package com.gui.toylanguage.model.types;

import com.gui.toylanguage.model.values.IntValue;
import com.gui.toylanguage.model.values.Value;

public class IntType implements Type{

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof IntType)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
