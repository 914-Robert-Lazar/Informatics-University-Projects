package com.gui.toylanguage.adt;

import java.util.LinkedList;
import java.util.List;

public class MyList<T> implements MyIList<T>{

    List<T> list;

    public MyList() {
        list = new LinkedList<T>();
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public void clear() {
        list.clear();
    }
    @Override
    public List<T> getList(){
        return list;
    }
    @Override
    public void setList(List<T> list) {
        this.list = list;
    }

}
