package textmenu.model.type;

import textmenu.model.value.RefValue;
import textmenu.model.value.Value;

public class RefType implements Type{
    private Type inner;
    public RefType(Type inner) {this.inner=inner;}

    public Type getInner() {return inner;}

    public boolean equalsTo(Object another){
        if (another instanceof RefType)
            return ((RefType)inner).equalsTo(((RefType)another).getInner());
        else
            return false;
    }
    public String toString() { return "Ref(" +inner.toString()+")";}
   public Value defaultValue() { return new RefValue(0,inner);}
}

