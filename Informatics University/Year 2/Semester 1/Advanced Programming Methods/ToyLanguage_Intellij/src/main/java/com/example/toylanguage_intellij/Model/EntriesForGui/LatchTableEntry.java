package com.example.toylanguage_intellij.Model.EntriesForGui;

import com.example.toylanguage_intellij.Model.Values.Value;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LatchTableEntry {
    private final SimpleStringProperty location;
    private final SimpleStringProperty value;


    public LatchTableEntry(Integer location, Integer value){
        this.location = new SimpleStringProperty(Integer.toString(location));
        this.value = new SimpleStringProperty(value.toString());
    }


    public String getLocation(){
        return this.location.get();
    }

    public String getValue(){
        return this.value.get();
    }

    public void setLocation(String newVariableName){
        this.location.set(newVariableName);
    }

    public void setValue(String newValue){
        this.value.set(newValue);
    }

    public StringProperty locationProperty() {
        return this.location;
    }

    public StringProperty valueProperty() {
        return this.value;
    }

}