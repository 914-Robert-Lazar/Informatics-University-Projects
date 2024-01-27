package com.gui.toylanguage.adt;

import java.util.List;

public interface MyIList<T> {

    void add(T v);
    void clear();

    List<T> getList();

    void setList(List<T> list);

}
