package com.gui.toylanguage.dto;

import com.gui.toylanguage.model.values.Value;
import javafx.beans.property.SimpleStringProperty;

public class SymTableEntry
{
    private SimpleStringProperty variableName;
    private SimpleStringProperty value;


    public SymTableEntry(String variableName, Value value){
        this.variableName = new SimpleStringProperty(variableName);
        this.value = new SimpleStringProperty(value.toString());
    }


    public String getVariableName(){
        return this.variableName.get();
    }

    public String getValue(){
        return this.value.get();
    }

    public void setVariableName(String newVariableName){
        this.variableName.set(newVariableName);
    }

    public void setValue(String newValue){
        this.value.set(newValue);
    }

}