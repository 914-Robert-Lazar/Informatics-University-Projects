package com.gui.toylanguage.model.values;

import com.gui.toylanguage.model.types.Type;

public interface Value {
    Type getType();

    Value deepCopy();
}
