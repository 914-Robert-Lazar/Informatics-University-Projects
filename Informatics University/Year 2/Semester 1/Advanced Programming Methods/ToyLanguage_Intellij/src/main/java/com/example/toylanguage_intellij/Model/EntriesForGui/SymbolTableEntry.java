package com.example.toylanguage_intellij.Model.EntriesForGui;

import com.example.toylanguage_intellij.Model.Values.Value;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SymbolTableEntry
{
    private final SimpleStringProperty variableName;
    private final SimpleStringProperty value;


    public SymbolTableEntry(String variableName, Value value){
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

    public StringProperty variableNameProperty() {
        return this.variableName;
    }

    public StringProperty valueProperty() {
        return this.value;
    }

}