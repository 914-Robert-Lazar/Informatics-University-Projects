package Model.Statements;

import Controller.MyException;
import Model.ProgramStateComponents.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState programState) throws MyException;
}