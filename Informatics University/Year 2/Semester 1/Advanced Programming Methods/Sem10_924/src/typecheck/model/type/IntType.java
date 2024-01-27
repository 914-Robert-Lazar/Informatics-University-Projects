package typecheck.model.type;

import typecheck.model.value.IntValue;
import typecheck.model.value.Value;

public class IntType implements Type{
    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString() {
        return "int";
    }
}
