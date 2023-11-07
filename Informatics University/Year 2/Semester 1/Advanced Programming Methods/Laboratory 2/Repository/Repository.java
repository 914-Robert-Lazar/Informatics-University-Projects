package Repository;
import Exceptions.CustomException;
import Model.Vehicle;

public class Repository implements IRepository{
    Vehicle[] vehicles;
    int size;

    public Repository()
    {
        this.size = 0;
        this.vehicles = new Vehicle[5];
    }

    @Override
    public void add(Vehicle vehicle) throws CustomException
    {
        this.vehicles[size] = vehicle;
        size++;
    }

    @Override
    public void remove(Vehicle vehicle) throws CustomException
    {
        int foundIndex = -1;
        for (int i = 0; i < this.size; ++i)
        {
            if (this.vehicles[i].equals(vehicle))
            {
                foundIndex = i;
                break;
            }
        }
        
        if (foundIndex == -1)
        {
            throw new CustomException("The vehicle does not exist.");
        }
        else
        {
            for (int i = foundIndex; i < this.size - 1; ++i)
            {
                this.vehicles[i] = this.vehicles[i + 1];
            }
            this.size--;
        }
    }

    @Override
    public Vehicle[] getAll()
    {
        return this.vehicles;
    }

    @Override
    public int getSize() {
        return this.size;
    }

}
