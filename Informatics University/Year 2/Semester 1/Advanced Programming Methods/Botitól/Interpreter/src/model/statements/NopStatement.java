package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.types.Type;

public class NopStatement implements Statement{

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new NopStatement();
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return new String("nop");
    }
}
