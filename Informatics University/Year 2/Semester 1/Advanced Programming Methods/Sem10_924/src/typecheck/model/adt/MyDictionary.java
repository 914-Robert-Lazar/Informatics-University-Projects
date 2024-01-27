package typecheck.model.adt;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements  MyIDictionary<K, V>{

    protected Map<K, V> map;

    public MyDictionary() {
        this.map = new HashMap<K, V>();
    }

    @Override
    public void put(K k, V v) throws MyException {
        map.put(k, v);
    }

    @Override
    public V lookup (K k) throws MyException{
        return map.get(k);
    }

    @Override
    public String toString() {
        return "MyDictionary:"+map.toString();
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<K, V> map) {
        this.map= map;
    }
}
