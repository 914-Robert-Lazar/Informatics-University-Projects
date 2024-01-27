package view.Commands;

import controller.Controller;
import exceptions.InterpreterException;

public class RunExample extends Command{
    private Controller controller;

    public RunExample(String key, String desc, Controller controller){
        super(key, desc);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            controller.typeCheck();
            controller.allSteps();
        } catch (InterpreterException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
