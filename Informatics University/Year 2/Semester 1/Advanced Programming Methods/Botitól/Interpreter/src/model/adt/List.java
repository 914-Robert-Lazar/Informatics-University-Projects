package model.adt;

import model.values.Value;

import java.util.ArrayList;
import java.util.Vector;

public class List<T> implements ListInterface<T> {
    private java.util.List<T> list;
    public List(){
        list=new Vector<T>();
    }

    @Override
    public void add(T value) {
        list.add(value);
    }

    @Override
    public java.util.List<T> getList() {
        return list;
    }


}
