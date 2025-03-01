package com.example.toylanguage_intellij.Model.Values;

import com.example.toylanguage_intellij.Model.Types.StringType;
import com.example.toylanguage_intellij.Model.Types.Type;

public class StringValue implements Value {
    String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringValue && this.value == ((StringValue) other).value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString()
    {
        return "" + this.value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }
    
}
