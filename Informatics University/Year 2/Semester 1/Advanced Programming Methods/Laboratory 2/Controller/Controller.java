package Controller;

import Exceptions.CustomException;
import Model.Vehicle;
import Repository.IRepository;

public class Controller {
    IRepository repository;

    public Controller(IRepository repository)
    {
        this.repository = repository;
    }

    public void add(Vehicle vehicle) throws CustomException
    {
        this.repository.add(vehicle);
    }

    public void remove(Vehicle vehicle) throws CustomException
    {
        this.repository.remove(vehicle);
    }

    public Vehicle[] filterByColor(String color)
    {
        Vehicle[] vehicles = this.repository.getAll();

        Vehicle[] filteredVehicles = new Vehicle[5];
        int size = 0;

        for (int i = 0; i < this.getRepositorySize(); ++i)
        {
            if (vehicles[i].getColor().equals(color))
            {
                filteredVehicles[size++] = vehicles[i];
            }
        }

        return filteredVehicles;
    }

    public Vehicle[] getAll()
    {
        return this.repository.getAll();
    }

    public int getRepositorySize()
    {
        return this.repository.getSize();
    }
}
