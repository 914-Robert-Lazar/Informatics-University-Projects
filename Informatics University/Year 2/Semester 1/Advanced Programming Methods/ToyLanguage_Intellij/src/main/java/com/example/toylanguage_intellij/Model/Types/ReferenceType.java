package com.example.toylanguage_intellij.Model.Types;

import com.example.toylanguage_intellij.Model.Values.ReferenceValue;
import com.example.toylanguage_intellij.Model.Values.Value;

public class ReferenceType implements Type {
    Type inner;

    public ReferenceType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return this.inner;
    }

    public boolean equals(Object another) {
        if (another instanceof ReferenceType) {
            return inner.equals(((ReferenceType) another).getInner());
        }
        else {
            return false;
        }
    }

    @Override 
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(0, inner);
    }
    
}
