package model.adt;

import exception.InterpreterException;
import model.values.Value;

import java.util.HashMap;
import java.util.Map;

public interface HeapInterface<Integer,Value> {
    Integer getFreePosition();
    Map<Integer, Value> getHeap();
    Integer add(Value value);
    boolean isDefined(Integer key);
    void update(Integer pos,Value value) throws InterpreterException;
    Value get(Integer pos) throws InterpreterException;
    String toStringElem(Integer key, Value value);
    void setContent(Map<Integer,Value> map);
}
