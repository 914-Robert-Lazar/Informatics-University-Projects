package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.adt.StackInterface;
import model.types.Type;

public class CompoundStatement implements Statement{
    private Statement statement1;
    private Statement statement2;
    public CompoundStatement(Statement first,Statement second){
        statement1=first;
        statement2=second;
    }
    public ProgramState execute(ProgramState state)throws InterpreterException {
        //TODO: STack operations
        StackInterface<Statement> executionStack=state.getExecutionStack();
        executionStack.push(statement2);
        executionStack.push(statement1);
        return null;
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new CompoundStatement(statement1.deepCopy(),statement2.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        return statement2.typeCheck(statement1.typeCheck(typeEnvironment));
    }

    @Override
    public String toString() {
        return statement1.toString() + statement2.toString();
    }
}
