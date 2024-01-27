package com.gui.toylanguage.model.values;


import com.gui.toylanguage.model.types.RefType;
import com.gui.toylanguage.model.types.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }
    public Type getLocationType(){
        return locationType;
    }
    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType);
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) { this.address = address; }

    @Override
    public String toString(){
        return "( " + address + " -> " + locationType.toString() + " );";
    }
}
