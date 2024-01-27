package typecheck.model.value;

import typecheck.model.type.RefType;
import typecheck.model.type.Type;

public class RefValue implements Value{
    private int address;
    private Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {return address;}
    public Type getType() { return new RefType(locationType);}

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }
}
