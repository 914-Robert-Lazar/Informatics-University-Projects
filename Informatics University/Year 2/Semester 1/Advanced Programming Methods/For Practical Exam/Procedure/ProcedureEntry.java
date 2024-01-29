package com.example.toylanguage_intellij.Model.EntriesForGui;

import com.example.toylanguage_intellij.Model.Statements.IStatement;
import javafx.beans.property.SimpleStringProperty;

public class ProcedureEntry {
    private final SimpleStringProperty definition;
    private final SimpleStringProperty body;


    public ProcedureEntry(ProcedureDefinition definition, IStatement body){
        this.definition = new SimpleStringProperty(definition.toString());
        this.body = new SimpleStringProperty(body.toString());
    }


    public String getDefinition(){
        return this.definition.get();
    }

    public String getBody(){
        return this.body.get();
    }

    public void setDefinition(String newVariableName){
        this.definition.set(newVariableName);
    }

    public void setBody(String newValue){
        this.body.set(newValue);
    }
}
