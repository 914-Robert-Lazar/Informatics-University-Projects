package com.example.toylanguage_intellij.Model.Values;

import com.example.toylanguage_intellij.Model.Types.ReferenceType;
import com.example.toylanguage_intellij.Model.Types.Type;

public class ReferenceValue implements Value {
    int address;
    Type locationType;

    public ReferenceValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ReferenceValue && this.address == ((ReferenceValue) other).address;
    }

    public int getAddress() {
        return this.address;
    }

    @Override
    public String toString()
    {
        return "(" + this.address + ", " + this.locationType + ")";
    }
    @Override
    public Type getType() {
        return new ReferenceType(this.locationType);
    }

    public Type getLocationType() {
        return this.locationType;
    }
    
}
