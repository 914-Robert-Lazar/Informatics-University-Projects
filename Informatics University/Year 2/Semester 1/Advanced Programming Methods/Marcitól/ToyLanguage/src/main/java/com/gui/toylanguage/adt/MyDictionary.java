package com.gui.toylanguage.adt;


import com.gui.toylanguage.exceptions.MyException;

import java.util.HashMap;

public class MyDictionary<K,V> implements MyIDictionary<K,V>{
    HashMap<K,V> dictionary;


    public MyDictionary(){
        dictionary = new HashMap<K, V>();
    }
    @Override
    public HashMap<K,V> getDictionary(){
        return dictionary;
    }

    @Override
    public void setDictionary(HashMap<K, V> newDictionary) {
        this.dictionary = newDictionary;
    }


    @Override
    public void add(K k, V v) {
        dictionary.put(k, v);
    }

    @Override
    public void update(K k, V v) throws MyException {
        if(!isDefined(k))
            throw new MyException("Variable " + (String)k +  " undefined");
        dictionary.put(k, v);
    }

    @Override
    public V lookUp(K k) throws MyException {
        if(!isDefined(k))
            throw new MyException("Variable undefined");
        return dictionary.get(k);
    }

    @Override
    public boolean isDefined(K k) {
        return dictionary.get(k) != null;
    }

    @Override
    public void remove(K k) throws MyException {
        if(!isDefined(k))
            throw new MyException("Variable undefined");
        dictionary.remove(k);
    }
}
