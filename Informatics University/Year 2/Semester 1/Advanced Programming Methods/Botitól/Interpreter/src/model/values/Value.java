package model.values;

import model.types.Type;

public interface Value {
    public Type getType();
    Value deepCopy();
}
