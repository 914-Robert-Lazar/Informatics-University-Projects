package Model.Statements;

import Model.ProgramStateComponents.IExecutionStack;
import Model.ProgramStateComponents.ProgramState;

public class CompoundStatement implements IStatement {
    IStatement first;
    IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }


    @Override
    public ProgramState execute(ProgramState programState) {
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();
        executionStack.push(second);
        executionStack.push(first);
        return null;
    }
}
