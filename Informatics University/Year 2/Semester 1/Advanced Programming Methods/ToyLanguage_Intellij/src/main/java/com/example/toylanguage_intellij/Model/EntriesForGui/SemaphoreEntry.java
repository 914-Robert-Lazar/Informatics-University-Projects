package com.example.toylanguage_intellij.Model.EntriesForGui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class SemaphoreEntry {
    private final SimpleStringProperty index;
    private final SimpleStringProperty value;

    private final SimpleStringProperty list;


    public SemaphoreEntry(Integer index, Integer value, List<Integer> list){
        this.index = new SimpleStringProperty(Integer.toString(index));
        this.value = new SimpleStringProperty(value.toString());
        this.list = new SimpleStringProperty(list.toString());
    }


    public String getIndex(){
        return this.index.get();
    }

    public String getValue(){
        return this.value.get();
    }
    public String getList(){
        return this.list.get();
    }

    public void setIndex(String newVariableName){
        this.index.set(newVariableName);
    }

    public void setValue(String newValue){
        this.value.set(newValue);
    }

    public void setList(String newList){
        this.list.set(newList);
    }

    public StringProperty indexProperty() {
        return this.index;
    }

    public StringProperty valueProperty() {
        return this.value;
    }
}
