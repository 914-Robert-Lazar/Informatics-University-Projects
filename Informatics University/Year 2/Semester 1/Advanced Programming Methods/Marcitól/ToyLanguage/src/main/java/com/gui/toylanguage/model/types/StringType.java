package com.gui.toylanguage.model.types;

import com.gui.toylanguage.model.values.StringValue;
import com.gui.toylanguage.model.values.Value;

public class StringType implements Type{
    @Override
    public Type deepCopy() {
        return new StringType();
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StringType)
            return true;
        return false;
    }
    @Override
    public String toString() {
        return "string";
    }
}
