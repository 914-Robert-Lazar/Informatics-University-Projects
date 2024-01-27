package com.gui.toylanguage.dto;

import com.gui.toylanguage.model.values.Value;
import javafx.beans.property.SimpleStringProperty;

public class HeapEntry {

    private SimpleStringProperty heapAddress;
    private SimpleStringProperty heapValue;

    public HeapEntry(Integer heapAddress, Value heapValue){
        this.heapAddress = new SimpleStringProperty(Integer.toString(heapAddress));
        this.heapValue = new SimpleStringProperty(heapValue.toString());
    }


    public String getHeapAddress(){
        return this.heapAddress.get();
    }

    public String getHeapValue(){
        return this.heapValue.get();
    }

    public void setHeapAddress(Integer newHeapAddress){
        this.heapAddress.set(String.valueOf(newHeapAddress));
    }

    public void setHeapValue(Value newHeapValue){
        this.heapValue.set(newHeapValue.toString());
    }

}
