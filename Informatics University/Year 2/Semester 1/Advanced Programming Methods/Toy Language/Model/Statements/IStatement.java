package Model.Statements;

import java.io.FileNotFoundException;
import java.io.IOException;

import Controller.MyException;
import Model.ProgramStateComponents.IDictionary;
import Model.ProgramStateComponents.ProgramState;
import Model.Types.Type;

public interface IStatement {
    ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException;
    IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException;
}