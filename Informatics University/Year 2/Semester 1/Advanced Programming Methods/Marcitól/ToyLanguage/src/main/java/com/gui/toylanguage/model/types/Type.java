package com.gui.toylanguage.model.types;

import com.gui.toylanguage.model.values.Value;

public interface Type {
    Type deepCopy();

    Value defaultValue();
}
