package model.Statements;

import exceptions.InterpreterException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;
import model.ProgramState;
import model.Types.Type;


public class CompoundStatement implements StatementInterface{
    private final StatementInterface first;
    private final StatementInterface second;

    public CompoundStatement(StatementInterface first, StatementInterface second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString(){
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyStackInterface<StatementInterface> stack = state.getExeStack();
        stack.push(this.second);
        stack.push(this.first);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new CompoundStatement(this.first, this.second);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        return second.typecheck(first.typecheck(table));
    }
}
