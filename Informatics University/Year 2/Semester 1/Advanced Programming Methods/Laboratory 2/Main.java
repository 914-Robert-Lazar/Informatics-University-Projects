import java.util.Scanner;

import Controller.Controller;
import Exceptions.CustomException;
import Repository.IRepository;
import Repository.Repository;
import View.View;

public class Main
{
    public static void main(String[] args) throws CustomException
    {
        IRepository repository = new Repository();
        Controller controller = new Controller(repository);
        Scanner scanner = new Scanner(System.in);
        View view = new View(controller, scanner);
        view.debugRun();
    }
}