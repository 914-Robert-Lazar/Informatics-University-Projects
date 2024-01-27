package com.example.toylanguage_intellij.Model.Types;

import com.example.toylanguage_intellij.Model.Values.IntegerValue;
import com.example.toylanguage_intellij.Model.Values.Value;

public class IntegerType implements Type {
    @Override
    public boolean equals(Object other) {
        return other instanceof IntegerType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntegerValue(0);
    }

}
