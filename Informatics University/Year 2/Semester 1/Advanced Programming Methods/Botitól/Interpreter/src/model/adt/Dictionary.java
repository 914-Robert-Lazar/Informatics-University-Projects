package model.adt;

import java.util.HashMap;

public class Dictionary<T1,T2> implements DictionaryInterface<T1,T2>{
    private HashMap<T1,T2> symbolsTable;
    public Dictionary(){
        symbolsTable=new HashMap<>();
    }
    @Override
    public T2 lookup(T1 key) {
        return symbolsTable.get(key);

    }

    @Override
    public boolean isDefined(T1 key) {
        return symbolsTable.containsKey(key);
    }

    @Override
    public void update(T1 key, T2 value) {
        symbolsTable.put(key,value);
    }

    public HashMap<T1,T2> getDictionary(){
        return symbolsTable;
    }

    @Override
    public T2 delete(T1 key) {
        return symbolsTable.remove(key);
    }
    @Override
    public DictionaryInterface<T1,T2> copy(){
        DictionaryInterface<T1,T2> copied=new Dictionary<>();
        for (T1 key:this.symbolsTable.keySet()
             ) {
            copied.update(key,this.lookup(key));
        }
        return copied;
    }

    public String toStringElem(T1 key, T2 value){
        var str=new StringBuffer();
        str.append(String.format("%s=%s",key,value.toString()));
        return str.toString();
    }

}
