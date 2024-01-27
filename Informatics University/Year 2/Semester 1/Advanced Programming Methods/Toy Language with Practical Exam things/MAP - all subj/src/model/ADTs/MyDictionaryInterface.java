package model.ADTs;

import exceptions.AdtException;
import model.Values.Value;

import java.util.Collection;
import java.util.Map;

public interface MyDictionaryInterface<T1, T2> {
    void add(T1 key, T2 val) throws AdtException;
    T2 remove(T1 key) throws AdtException;
    void update(T1 key, T2 val) throws AdtException;
    T2 lookup(T1 key) throws AdtException;
    boolean isEmpty();
    boolean isDefined(T1 key); // if the key is in dict

    Collection<T2> valuesAsCollection();
    Collection<T1> keysAsCollection();
    Map<T1, T2> getContent();

    MyDictionaryInterface<T1,T2> deepCopy() throws AdtException;
}
