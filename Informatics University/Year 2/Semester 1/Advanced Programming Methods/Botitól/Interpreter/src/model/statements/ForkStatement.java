package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.adt.Stack;
import model.adt.StackInterface;
import model.types.Type;

public class ForkStatement implements Statement{
    Statement statement;
    public ForkStatement(Statement statement){
        this.statement=statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        return new ProgramState(new Stack<Statement>(), state.getSymbolsTable().copy(), state.getOut(),state.getFileTable(),state.getHeapTable(),statement);
    }

    @Override
    public String toString() {
        return "fork("+statement+");";
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        statement.typeCheck(typeEnvironment.copy());
        return typeEnvironment;
    }
}
