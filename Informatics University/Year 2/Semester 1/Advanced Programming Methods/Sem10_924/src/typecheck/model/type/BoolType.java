package typecheck.model.type;

import typecheck.model.value.BoolValue;
import typecheck.model.value.Value;

public class BoolType implements Type{
    @Override
    public Value defaultValue() {
        return new BoolValue(true);
    }

    @Override
    public String toString() {
        return "bool";
    }
}
