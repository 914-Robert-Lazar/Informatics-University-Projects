package Model.ProgramStateComponents;

import java.util.HashMap;
import java.util.Map;
public class SymbolTable<Key, Val> implements IDictionary<Key, Val>{
    Map<Key, Val> map;

    public SymbolTable() {
        map = new HashMap<Key, Val>();
    }

    @Override
    public Val findValue(Key key) {
        return map.get(key);
    }

    @Override
    public void put(Key key, Val value) {
        map.put(key, value);
    }

    @Override
    public boolean isDefined(Key key) {
        return map.get(key) != null;
    }
    
    @Override
    public String toString() {
        return "MyDictionary{" +
                "map=" + map +
                '}';
    }

    public Map<Key, Val> getMap() {
        return map;
    }

    public void setMap(Map<Key, Val> map) {
        this.map = map;
    }

    @Override
    public void remove(Key key) {
        this.map.remove(key);
    }

    @Override
    public IDictionary<Key, Val> copy() {
        IDictionary<Key, Val> copied = new SymbolTable<>();
        for (Key key:this.map.keySet()) {
            copied.put(key, this.findValue(key));
        }
        return copied;
    }
}
