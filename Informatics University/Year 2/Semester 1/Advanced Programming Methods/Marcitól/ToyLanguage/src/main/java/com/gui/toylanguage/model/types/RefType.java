package com.gui.toylanguage.model.types;

import com.gui.toylanguage.model.values.RefValue;
import com.gui.toylanguage.model.values.Value;

public class RefType implements Type{

    private Type innerType;

    public RefType(Type innerType) {
        this.innerType = innerType;
    }

    @Override
    public Type deepCopy() {
        return new RefType(innerType);
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0,innerType);
    }

    public Type getInnerType() {
        return innerType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType)
            return (innerType).equals(((RefType)obj).getInnerType());
        return false;
    }

    @Override
    public String toString() {
        return "ref(" + innerType.toString() + ")";
    }
}
