package model.Types;

import model.Values.RefValue;
import model.Values.Value;

public class RefType implements Type{
    private final Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return this.inner;
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof RefType)
            return this.inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref("+ this.inner.toString() + ")";
    }

    @Override
    public Value getDefault() {
        return new RefValue(0, this.inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(this.inner);
    }
}
