package model.adt;

import model.values.Value;

import java.util.HashMap;

public interface DictionaryInterface<T1,T2> {
    T2 lookup(T1 key);
    boolean isDefined(T1 key);
    void update(T1 key,T2 value);
    HashMap<T1,T2> getDictionary();
    T2 delete(T1 key);
    String toStringElem(T1 key, T2 value);
    DictionaryInterface<T1,T2> copy();
}
