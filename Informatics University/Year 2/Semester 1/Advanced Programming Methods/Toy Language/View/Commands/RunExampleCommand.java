package View.Commands;

import java.io.IOException;

import Controller.Controller;
import Controller.MyException;

public class RunExampleCommand extends Command {
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        }
        catch (MyException e) {
            e.printMessage();
        }
        catch (IOException e) {
            System.out.println("The file did not opened.");
        }
    }
    
}
