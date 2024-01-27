package com.example.toylanguage_intellij.Model.EntriesForGui;

import com.example.toylanguage_intellij.Model.Values.Value;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HeapEntry {

    private final SimpleStringProperty heapAddress;
    private final SimpleStringProperty heapValue;

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

    public StringProperty heapAddressProperty() {
        return this.heapAddress;
    }

    public StringProperty heapValueProperty() {
        return this.heapValue;
    }
}
