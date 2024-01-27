package model.Statements;

import exceptions.InterpreterException;
import model.ADTs.MyDictionaryInterface;
import model.ProgramState;
import model.Types.Type;

public class NopStatement implements StatementInterface{
    public NopStatement() {}

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new NopStatement();
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        return table;
    }

    @Override
    public String toString() {
        return "";
    }
}
