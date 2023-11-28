package textmenu.model.adt;

import java.util.Map;

public interface MyIDictionary<K, V> {

    void put (K k, V v) throws MyException;
    V lookup (K k) throws MyException;
    Map<K, V> getContent();
    void setContent(Map<K, V> map);
}