package model.Statements;


import exceptions.AdtException;
import exceptions.InterpreterException;
import model.ADTs.MyDictionary;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStack;
import model.ADTs.MyStackInterface;
import model.ProgramState;
import model.Types.Type;
import model.Values.Value;

import java.util.Map;

public class ForkStatement implements StatementInterface{
    private final StatementInterface forkStatement;

    public ForkStatement(StatementInterface forkStatement){
        this.forkStatement = forkStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyStackInterface<StatementInterface> newExeStack = new MyStack<>();
        newExeStack.push(forkStatement);
        MyDictionaryInterface<String, Value> newSymTable;
        newSymTable = state.getSymbolTable().deepCopy();

        //THIS IS THE GOOD ONE: ProgramState newProgramState = new ProgramState(newExeStack, newSymTable, state.getOut(), state.getFileTable(), state.getHeap(), state.getLockTable())
        ProgramState newProgramState = new ProgramState(newExeStack, newSymTable, state.getOut(), state.getFileTable(), state.getHeap(), state.getLockTable(), state.getLatchTable(), state.getCountSemaphoreTable(), state.getToySemaphoreTable());
        newProgramState.setId();
        return newProgramState;
    }


    @Override
    public StatementInterface deepCopy() {
        return new ForkStatement(this.forkStatement);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        forkStatement.typecheck(table.deepCopy());
        return table;
    }

    @Override
    public String toString() {
        return "fork(" + this.forkStatement.toString() + ")";
    }

}
