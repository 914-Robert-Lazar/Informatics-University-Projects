package model.Statements;

import exceptions.InterpreterException;
import model.ADTs.MyDictionaryInterface;
import model.ProgramState;
import model.Types.Type;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws InterpreterException;
    // which is the execution method for a statement
    StatementInterface deepCopy();
    MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException;
}
