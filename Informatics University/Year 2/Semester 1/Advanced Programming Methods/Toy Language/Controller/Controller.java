package Controller;
import Model.ProgramStateComponents.IExecutionStack;
import Model.ProgramStateComponents.ProgramState;
import Model.Statements.IStatement;
import Repository.IRepository;

public class Controller {
    private IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void addProgramToRepository(ProgramState programState) {
        this.repository.add(programState);
    } 

    public ProgramState oneStep(ProgramState programState) throws MyException {
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();

        if (executionStack.isEmpty()) {
            throw new MyException("ProgramState Stack is empty.");
        }

        IStatement currStatement = executionStack.pop();
        return currStatement.execute(programState);
    }

    public void allStepByStep() throws MyException {
        ProgramState programState = repository.getCurrentProgram();
        System.out.println(programState);

        while (!programState.getExecutionStack().isEmpty()) {
            oneStep(programState);
            System.out.println(programState);
        }
    }

    public void allStep() throws MyException {
        ProgramState programState = repository.getCurrentProgram();

        while (!programState.getExecutionStack().isEmpty()) {
            oneStep(programState);
        }
        System.out.println(programState);
    }
}
