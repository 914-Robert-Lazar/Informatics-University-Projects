package model.values;

import model.types.ReferenceType;
import model.types.Type;

public class ReferenceValue implements Value{
    private Type locationType;
    private int address;
    public ReferenceValue(int address,Type locationType){
        this.locationType=locationType;
        this.address=address;
    }
    public int getAddress(){
        return address;
    }
    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }
    public Type getLocationType(){
        return locationType;
    }
    @Override
    public String toString() {
        return "ReferenceValue("+address+"->"+locationType+")";
    }

    @Override
    public Value deepCopy() {
        return new ReferenceValue(address,locationType);
    }
}
