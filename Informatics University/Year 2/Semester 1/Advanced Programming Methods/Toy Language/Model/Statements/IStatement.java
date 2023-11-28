package Model.Statements;

import java.io.FileNotFoundException;
import java.io.IOException;

import Controller.MyException;
import Model.ProgramStateComponents.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException;
}