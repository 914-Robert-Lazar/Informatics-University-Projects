package com.example.toylanguage_intellij.Model.Values;

import com.example.toylanguage_intellij.Model.Types.BooleanType;
import com.example.toylanguage_intellij.Model.Types.Type;

public class BooleanValue implements Value {
    boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BooleanValue && this.value == ((BooleanValue) other).value;
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
