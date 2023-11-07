package Model.Statements;

import Controller.MyException;
import Model.ProgramStateComponents.ProgramState;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        return programState;
    }
    
    @Override
    public String toString() {
        return "()";
    }
}
