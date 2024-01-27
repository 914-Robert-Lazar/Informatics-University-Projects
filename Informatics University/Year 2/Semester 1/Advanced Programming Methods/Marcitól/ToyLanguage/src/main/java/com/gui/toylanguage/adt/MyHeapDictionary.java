package com.gui.toylanguage.adt;

import com.gui.toylanguage.model.values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeapDictionary extends MyDictionary<Integer, Value>{

    private int nextAddress;

    public MyHeapDictionary(){
        this.dictionary = new HashMap<Integer, Value>();
        nextAddress = 0;
    }

    private int nextFreeAddress(){
        return ++nextAddress;
    }

    public int put(Value v){
        this.dictionary.put(nextFreeAddress(), v);
        return nextAddress;
    }
    @Override
    public void add(Integer k,Value v){
        this.dictionary.put(nextFreeAddress(), v);
    }

    public void setContent(Map<Integer, Value> map) {
        this.dictionary = (HashMap<Integer, Value>) map;
    }

}
