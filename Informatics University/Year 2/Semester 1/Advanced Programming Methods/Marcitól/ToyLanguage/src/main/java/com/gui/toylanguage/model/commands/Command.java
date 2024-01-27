package com.gui.toylanguage.model.commands;


import com.gui.toylanguage.exceptions.MyException;

public abstract class Command {
    protected String key, description;
    public Command(String key, String description){
        this.key = key;
        this.description = description;
    }
    public abstract void execute() throws MyException;

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
