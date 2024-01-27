package typecheck.model.type;

import typecheck.model.value.RefValue;
import typecheck.model.value.Value;

public class RefType implements Type{
    private Type inner;
    public RefType(Type inner) {this.inner=inner;}

    public Type getInner() {return inner;}

    public boolean equals(Object another){
        if (another instanceof RefType)
            return inner.toString().equals(((RefType)another).getInner().toString());
        else
            return false;
    }


   public String toString() { return "Ref(" +inner.toString()+")";}
   public Value defaultValue() { return new RefValue(0,inner);}
}
