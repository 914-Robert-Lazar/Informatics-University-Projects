package textmenu.model.type;

import textmenu.model.value.BoolValue;
import textmenu.model.value.IntValue;
import textmenu.model.value.Value;

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

