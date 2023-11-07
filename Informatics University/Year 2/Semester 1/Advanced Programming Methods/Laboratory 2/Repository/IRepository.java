package Repository;
import Exceptions.CustomException;
import Model.Vehicle;

public interface IRepository {
    void add(Vehicle vehicle) throws CustomException;
    void remove(Vehicle vehicle) throws CustomException;
    Vehicle[] getAll();
    int getSize();
}
