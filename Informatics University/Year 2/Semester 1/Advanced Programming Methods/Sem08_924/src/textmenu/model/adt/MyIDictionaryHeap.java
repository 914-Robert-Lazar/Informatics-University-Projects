package textmenu.model.adt;

import java.util.Map;

public interface MyIDictionaryHeap<V> {

    int put (V v) throws MyException;
    //int allocate(V value);

    void setContent(Map<Integer, V> map);
    Map<Integer,V> getContent();

}

