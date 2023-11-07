package View;

import java.util.Scanner;

import Controller.Controller;
import Exceptions.CustomException;
import Model.Car;
import Model.Motorcycle;
import Model.Undefined;
import Model.Vehicle;
import Model.Bicycle;

public class View {
    Controller controller;
    Scanner scanner;

    public View(Controller controller, Scanner scanner)
    {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void debugRun() throws CustomException
    {
        this.controller.add(new Bicycle("red"));
        this.controller.add(new Car("red"));
        this.controller.add(new Motorcycle("blue"));
        this.controller.add(new Car("yellow"));
        this.controller.add(new Car("red"));

        this.controller.remove(new Car("red"));
        this.controller.add(new Bicycle("blue"));

        this.run();
    }

    public void run() throws CustomException
    {
        while (true)
        {
            this.printMenu();
            try 
            {
                String option = this.readOption();
                switch(option)
                {
                    case "0":
                    {
                        return;
                    }
                    case "1":
                    {
                        this.addVehicle();
                        break;
                    }
                    case "2":
                    {
                        this.removeVehicle();
                        break;
                    }
                    case "3":
                    {
                        this.filterVehicles();
                        break;
                    }
                    case "4":
                    {
                        this.printAll();
                        break;
                    }
                    default:
                    {
                        System.out.println("Invalid option.");
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("The parking slot is already full.");
            }
            catch (CustomException e)
            {
                e.printMessage();
            }
        }
    }

    private Vehicle getData() throws CustomException
    {
        System.out.print("Type of vehicle(Car, Motorcycle, Bicycle): ");
        String type = this.scanner.nextLine();
        
        Vehicle vehicle = new Undefined();
        switch(type)
        {
            case "Car":
            { 
                vehicle = new Car();
                break; 
            }
            case "Motorcycle":
            {
                vehicle = new Motorcycle();
                break; 
            }
            case "Bicycle":
            {
                vehicle = new Bicycle();
                break; 
            }
            default:
            {
                throw new CustomException("Invalid type.");
            }
        }
        System.out.print("Color of vehicle: ");
        String color = scanner.nextLine();
        vehicle.setColor(color);
        
        return vehicle;
    }

    private void printAll()
    {
        Vehicle[] vehicles = this.controller.getAll();
        for (int i = 0; i < this.controller.getRepositorySize(); ++i)
        {
            System.out.println(vehicles[i]);
        }
    }

    private void filterVehicles() {
        
        System.out.println("Color to filter by: ");
        String color = this.scanner.nextLine();
        Vehicle[] filteredVehicles = this.controller.filterByColor(color);
        for (int i = 0; i < this.controller.getRepositorySize() && filteredVehicles[i] != null; ++i)
        {
            System.out.println(filteredVehicles[i]);
        }
    }

    private void removeVehicle() throws CustomException {
        Vehicle vehicle = this.getData();
        if (vehicle instanceof Undefined)
        {
            return;
        }
        this.controller.remove(vehicle);
    }

    private void addVehicle() throws CustomException
    {
        Vehicle vehicle = this.getData();
        if (vehicle instanceof Undefined)
        {
            return;
        }
        this.controller.add(vehicle);
    }

    private void printMenu() 
    {
        System.out.println("\nParking slot menu");
        System.out.println("0. Exit");
        System.out.println("1. Add a vehicle");
        System.out.println("2. Remove a vehicle");
        System.out.println("3. Filter vehicles by color");
        System.out.println("4. See all vehicles.");
    }

    public String readOption()
    {
        System.out.print("Give option: ");
        if (this.scanner.hasNextLine())
        {
            return this.scanner.nextLine();
        }
        return "";
    };
}
