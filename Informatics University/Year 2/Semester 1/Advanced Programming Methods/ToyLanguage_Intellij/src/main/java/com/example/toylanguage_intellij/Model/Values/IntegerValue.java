package com.example.toylanguage_intellij.Model.Values;

import com.example.toylanguage_intellij.Model.Types.IntegerType;
import com.example.toylanguage_intellij.Model.Types.Type;

public class IntegerValue implements Value{
    int value;

    public IntegerValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntegerValue && this.value == ((IntegerValue) other).value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString()
    {
        return "" + this.value;
    }

    @Override
    public Type getType() {
        return new IntegerType();
    }
    
}
