package com.gui.toylanguage.model.values;


import com.gui.toylanguage.model.types.BoolType;
import com.gui.toylanguage.model.types.Type;

public class BoolValue implements Value{
    private final boolean val;
    public BoolValue(boolean v){
        val = v;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }
    @Override
    public boolean equals(Object o) {
        if(o instanceof BoolValue && ((BoolValue) o).getVal() == val)
            return true;
        return false;
    }
    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    public boolean getVal() {
        return val;
    }
    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
