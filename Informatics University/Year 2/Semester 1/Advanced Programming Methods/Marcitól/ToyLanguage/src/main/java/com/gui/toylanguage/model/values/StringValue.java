package com.gui.toylanguage.model.values;


import com.gui.toylanguage.model.types.StringType;
import com.gui.toylanguage.model.types.Type;

public class StringValue implements Value{
    private final String val;

    public StringValue(String val) {
        this.val = val;
    }
    public String getVal() {
        return val;
    }
    @Override
    public boolean equals(Object o) {
        if(o instanceof StringValue && ((StringValue) o).getVal().equals(val))
            return true;
        return false;
    }
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "'"+ val + "'";
    }
    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }
}
