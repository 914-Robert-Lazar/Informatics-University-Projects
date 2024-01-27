package Model.Statements;

import java.io.FileNotFoundException;
import java.io.IOException;

import Controller.MyException;
import Model.ProgramStateComponents.ExecutionStack;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.Type;

public class ForkStatement implements IStatement{
    IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException {
        return new ProgramState(new ExecutionStack<IStatement>(), programState.getSymTable().copy(), programState.getOut(), 
                                programState.getFileTable(), programState.getHeap(), statement);
    }
    
    @Override
    public String toString() {
        return "Fork(" + statement + ");";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv.copy());
        return typeEnv;
    }


}
