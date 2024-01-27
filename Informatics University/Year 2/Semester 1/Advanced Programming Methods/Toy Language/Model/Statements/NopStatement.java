package Model.Statements;

import Controller.MyException;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.Type;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        return null;
    }
    
    @Override
    public String toString() {
        return "()";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
