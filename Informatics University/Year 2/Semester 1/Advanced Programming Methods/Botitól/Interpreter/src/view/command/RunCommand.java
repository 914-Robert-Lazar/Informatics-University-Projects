package view.command;

import controller.Controller;
import exception.InterpreterException;

import java.io.IOException;

public class RunCommand extends Command{
    private Controller controller;
    public RunCommand(String key,String desc,Controller controller){
        super(key,desc);
        this.controller=controller;
    }
    @Override
    public void execute() {
        try {
            controller.executeAllSteps();
        }
         catch (InterpreterException e) {
            System.out.println(e.toString());
        }
    }
}
