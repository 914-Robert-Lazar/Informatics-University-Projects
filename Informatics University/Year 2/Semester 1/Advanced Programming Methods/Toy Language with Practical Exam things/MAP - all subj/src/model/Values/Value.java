package model.Values;

import model.Types.Type;

public interface Value {
    Type getType();
    Value deepCopy();
}
