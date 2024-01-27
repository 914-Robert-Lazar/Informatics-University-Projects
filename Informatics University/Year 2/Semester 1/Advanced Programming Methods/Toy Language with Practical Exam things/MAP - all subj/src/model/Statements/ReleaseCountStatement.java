package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import javafx.util.Pair;
import model.ADTs.MyCountSemaphoreInterface;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;
import model.ProgramState;
import model.Types.IntType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.Value;

import java.util.List;

public class ReleaseCountStatement implements StatementInterface{
    private final String var;

    public ReleaseCountStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyCountSemaphoreInterface<Pair<Integer, List<Integer>>> semTable = state.getCountSemaphoreTable();

        if(!symbolTable.isDefined(this.var))
            throw new StatementException("Variable is not in symbol table");
        Value varVal = symbolTable.lookup(this.var);
        if(!varVal.getType().equals(new IntType()))
            throw new StatementException("Variable's value is not of type int");

        int foundIndex = ((IntValue) varVal).getValue();

        if(!semTable.exists(foundIndex))
            throw new StatementException("Index does not exist");

        Pair<Integer, List<Integer>> pair = semTable.lookup(foundIndex);
        if(pair.getValue().contains(state.getId_thread()))
            pair.getValue().remove(state.getId_thread());

        state.setCountSemaphoreTable(semTable);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new ReleaseCountStatement(this.var);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        if(table.lookup(this.var).equals(new IntType()))
            return table;
        else throw new TypeException("Variable type is not int");
    }

    @Override
    public String toString() {
        return "countRelease (" + this.var + ")";
    }
}
