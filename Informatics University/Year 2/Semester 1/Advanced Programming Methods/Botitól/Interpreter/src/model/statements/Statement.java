package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.types.Type;

import java.io.IOException;

public interface Statement {
    public ProgramState execute(ProgramState state) throws InterpreterException;
    public Statement deepCopy() throws InterpreterException;
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String,Type> typeEnvironment) throws InterpreterException;
}
