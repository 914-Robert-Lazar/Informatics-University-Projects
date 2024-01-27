package com.gui.toylanguage.adt;

import com.gui.toylanguage.exceptions.MyException;

import java.util.HashMap;

public interface MyIDictionary<K,V> {
    void add(K k, V v);

    HashMap<K,V> getDictionary();
    void setDictionary(HashMap<K,V> newDictionary);
    void update(K k, V v) throws MyException;

    V lookUp(K k) throws MyException;

    boolean isDefined(K k);

    void remove(K k) throws MyException;
}
