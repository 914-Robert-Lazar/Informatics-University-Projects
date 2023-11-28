package textmenu.view;

import textmenu.controller.Controller;
import textmenu.model.adt.*;

public class RunExample extends Command {
    private Controller ctrl;
    public RunExample(String key, String desc, Controller ctrl){
        super(key, desc);
        this.ctrl=ctrl;
    }
    @Override
    public void execute() {
        try{
            ctrl.allStep(); }
        catch (MyException e) {
            System.out.println(e.getMessage());
        } //here you must treat the exceptions that can not be solved in the controller
    }
}
