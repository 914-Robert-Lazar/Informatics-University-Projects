import Controller.Controller;
import Repository.IRepository;
import Repository.Repository;
import View.View;

public class Main {
    public static void main(String[] args) {
        IRepository repository = new Repository();
        Controller controller = new Controller(repository);
        View view = new View(controller);
        view.menu();
    }
}
