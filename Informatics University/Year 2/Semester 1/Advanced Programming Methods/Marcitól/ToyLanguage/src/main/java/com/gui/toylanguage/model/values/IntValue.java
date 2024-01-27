package com.gui.toylanguage.model.values;



import com.gui.toylanguage.model.types.IntType;
import com.gui.toylanguage.model.types.Type;

import java.util.Objects;

public class IntValue implements Value{
    private final int val;
    public IntValue(int v){
        val = v;
    }
    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof IntValue && ((IntValue) o).getVal() == val)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }
}
