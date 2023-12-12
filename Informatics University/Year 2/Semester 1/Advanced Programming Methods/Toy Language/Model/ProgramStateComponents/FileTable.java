package Model.ProgramStateComponents;

import java.util.HashMap;
import java.util.Map;

public class FileTable<Key, Val> implements IDictionary<Key, Val>{

    Map<Key, Val> map;

    public FileTable() {
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
    public String toString() {
        return "FileTable{" +
                "map=" + map +
                '}';
    }

    @Override
    public IDictionary<Key, Val> copy() {
        IDictionary<Key, Val> copied = new FileTable<>();
        for (Key key:this.map.keySet()) {
            copied.put(key, this.findValue(key));
        }
        return copied;
    }
}
