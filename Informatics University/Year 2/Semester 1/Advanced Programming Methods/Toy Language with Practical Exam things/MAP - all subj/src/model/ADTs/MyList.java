package model.ADTs;

import exceptions.AdtException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class MyList<T> implements MyListInterface<T>{
    private final LinkedList<T> list;

    public MyList() {
        this.list = new LinkedList<>();
    }

    @Override
    public void add(T val) {
        this.list.add(val);
    }

    @Override
    public T remove(int poz) throws AdtException {
        try{
            return this.list.remove(poz);
        } catch (Exception e){
            throw new AdtException("Position index out of range");
        }
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public T get(int poz) throws AdtException{
        try{
            return this.list.get(poz);
        } catch (Exception e){
            throw new AdtException("Position index out of range");
        }
    }

    @Override
    public Iterator<T> iterator() {
        synchronized (this.list) {
            return list.iterator();
        }
    }

    @Override
    public void forEach(Consumer<? super T> action){
        synchronized (list) {
            list.forEach(action);
        }
    }

    @Override
    public java.util.List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
