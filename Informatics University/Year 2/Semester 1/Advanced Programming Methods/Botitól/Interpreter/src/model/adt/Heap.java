package model.adt;

import exception.InterpreterException;
import model.adt.HeapInterface;
import model.values.Value;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Heap implements HeapInterface<java.lang.Integer, model.values.Value> {
    private HashMap<Integer,Value> heap;
    private Integer freePosition;
    public Heap(){
        heap=new HashMap<>();
        freePosition=getFreePosition();
    }
    @Override
    public Integer getFreePosition() {
        int x=RandomGenerator.getDefault().nextInt(100);
        while(heap.containsKey(x)){
            x=RandomGenerator.getDefault().nextInt(100);
        }
        return x;
    }



    @Override
    public Map<Integer, Value> getHeap() {
        return heap;
    }

    @Override
    public Integer add(Value value) {
        Integer pos=freePosition;
        heap.put(freePosition,value);
        freePosition=getFreePosition();
        return pos;
    }

    @Override
    public boolean isDefined(Integer key) {
        return heap.containsKey(key);
    }

    @Override
    public void update(Integer pos, Value value) throws InterpreterException {
        if (heap.containsKey(pos)){
            heap.put(pos,value);
        }else{
            throw new InterpreterException("Position is not defined");
        }
    }

    @Override
    public Value get(Integer pos) throws InterpreterException {
        if (heap.containsKey(pos)){
            return heap.get(pos);
        }else{
            throw new InterpreterException("Position is not defined");
        }
    }
    public String toStringElem(Integer key, Value value){
        var str=new StringBuffer();
        str.append(String.format("%s->%s",key,value.toString()));
        return str.toString();
    }

    @Override
    public void setContent(Map<Integer, Value> map) {
        heap.clear();
        for (Integer key:map.keySet()
             ) {
            heap.put(key,map.get(key));
        }
    }
}