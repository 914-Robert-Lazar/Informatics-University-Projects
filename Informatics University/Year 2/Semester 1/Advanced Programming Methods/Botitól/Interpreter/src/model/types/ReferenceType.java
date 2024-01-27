package model.types;

import model.values.ReferenceValue;
import model.values.Value;

public class ReferenceType implements Type{
    private Type inner;
    public ReferenceType(Type inner){
        this.inner=inner;
    }
    public Type getInner(){
        return inner;
    }
    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof ReferenceType){
            return inner.equals(((ReferenceType) obj).getInner());
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "Ref("+inner+")";
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(0,inner);
    }
}
