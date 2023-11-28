package View.Commands;

public class ExitCommand extends Command {
    public ExitCommand(String key, String decription) {
        super(key, decription);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
    
}
