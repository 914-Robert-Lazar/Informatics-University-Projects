package model.Values;

import model.Types.RefType;
import model.Types.Type;

public class RefValue implements Value{
    private final int address;
    private final Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress(){
        return this.address;
    }

    @Override
    public Type getType() {
        return new RefType(this.locationType);
    }

    @Override
    public String toString() {
        return "(" + this.address + ", " + this.locationType + ")";
    }

    @Override
    public Value deepCopy() {
        return new RefValue(this.address, this.locationType);
    }
}
