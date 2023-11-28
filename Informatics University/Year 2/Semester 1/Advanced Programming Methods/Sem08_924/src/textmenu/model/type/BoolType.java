package textmenu.model.type;

import textmenu.model.value.BoolValue;
import textmenu.model.value.Value;

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

