package view;

import controller.Controller;
import exception.InterpreterException;

import java.io.IOException;
import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller _controller){
        controller=_controller;
    }
    public void printOptions(){
        System.out.println("1. Input a program");
        System.out.println("2. Execute a program");
    }
    public void runConsole() throws InterpreterException, IOException {
        while (true){
           printOptions();
            Scanner sc=new Scanner(System.in);
            int nr=sc.nextInt();
            if(nr==2){
                controller.executeAllSteps();
            }
        }
    }
}
