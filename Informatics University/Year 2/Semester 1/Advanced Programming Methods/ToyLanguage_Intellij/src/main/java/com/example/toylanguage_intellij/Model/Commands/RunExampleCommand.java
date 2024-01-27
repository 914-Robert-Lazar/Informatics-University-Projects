package com.example.toylanguage_intellij.Model.Commands;

import com.example.toylanguage_intellij.Controller.Controller;

public class RunExampleCommand extends Command {
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    public Controller getController() {
        return this.controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString(){
        return this.getDescription();
    }
    
}
