import repository.IRepository;
import repository.Repository;
import controller.Controller;
import View.View;

public class Main {
    public static void main(String[] args) {
        IRepository repository = new Repository();
        Controller controller = new Controller(repository);
        View view = new View(controller);
        view.run();
    }
}