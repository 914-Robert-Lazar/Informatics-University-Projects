package View;

import controller.Controller;
import model.*;
import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }
    public void printMenu(){
        System.out.println("MAIN MENU.");
        System.out.println("0. Exit");
        System.out.println("1. Add");
        System.out.println("2. Remove");
        System.out.println("3. All");
        System.out.println("4. Filter");
    };
    public void run(){
        while (true)
        {
            printMenu();
            String option = readOption();
            switch(option)
            {
                case "0":
                {

                    break;
                }
                case "1":
                {

                    break;
                }
                case "4":
                {
                    System.out.println("Give a value: ");
                    Scanner scanner = new Scanner(System.in);
                    //scanner.nextFloat();
                    controller.add(new Apple(5));
                    controller.add(new Apple(50));
                    controller.add(new Apple(25));
                    controller.add(new Apple(52));
                    controller.add(new Apple(55));
                    float x = Float.parseFloat(scanner.nextLine());
                    Item[] filteredItems = controller.filter(x);
                    for (Item i : filteredItems)
                    {
                        System.out.println(i);
                    }
                    break;
                }
            }
        }
    };
    public String readOption(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give option: ");
        return scanner.nextLine();
    };
}
