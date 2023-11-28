package textmenu.model.adt;

import java.util.HashMap;
import java.util.Map;

public class MyDictionaryHeap<V> implements MyIDictionaryHeap<V>{

    public static int nextAddress=0;

    protected Map<Integer, V> map;

    public MyDictionaryHeap() {
        this.map = new HashMap<Integer, V>();
    }

    private int nextFreeAddress(){
        nextAddress++;
        return nextAddress;
    }
    @Override
    public int put(V v) throws MyException {
        map.put(nextFreeAddress(), v);
        return nextAddress;
    }

    @Override
    public String toString() {
        return "MyDictionaryHeap:"+map.toString();
    }

    public void setContent(Map<Integer, V> map){
        this.map=map;
    }

    public Map<Integer,V> getContent(){
        return map;
    }
}

