package view.menu;

import exception.InterpreterException;
import view.command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu(){
        commands=new HashMap<>();
    }
    public void addCommand(Command c){
        commands.put(c.getKey(),c);
    }
    private void printMenu(){
        for (Command c:commands.values()
             ) {
            String line =String.format("%4s:%s",c.getKey(),c.getDescription());
            System.out.println(line);
        }
    }
    public void show(){
        Scanner scanner=new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if (command == null) {
                System.out.println("Invalid option");
                continue;
            }
            command.execute();
        }
    }
}
