package com.gui.toylanguage.model.commands;


import com.gui.toylanguage.exceptions.MyException;

public class ExitCommand extends Command{

    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() throws MyException {
        System.exit(0);
    }
}
