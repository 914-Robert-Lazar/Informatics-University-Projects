package com.gui.toylanguage.model.commands;


import com.gui.toylanguage.controller.Controller;
import com.gui.toylanguage.controller.Stepflag;
import com.gui.toylanguage.exceptions.MyException;

public class RunCommand extends Command {


    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public RunCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws MyException {
        controller.runSteps(Stepflag.ALLSTEPS);
    }
    public void executeOneStep()throws MyException {
        controller.runSteps(Stepflag.EACHSTEP);
    }

    @Override
    public String toString(){
        return this.description;
    }
}
