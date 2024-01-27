package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import javafx.util.Pair;
import model.ADTs.MyCountSemaphoreInterface;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;
import model.ADTs.MyToySempahoreInterface;
import model.ProgramState;
import model.Triplet;
import model.Types.IntType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.Value;

import java.util.List;

public class AquireToyStatement implements StatementInterface{
    private final String var;

    public AquireToyStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyToySempahoreInterface<Triplet> semTable = state.getToySemaphoreTable();
        MyStackInterface<StatementInterface> stack = state.getExeStack();

        if(!symbolTable.isDefined(this.var))
            throw new StatementException("Variable is not in symbol table");
        Value varVal = symbolTable.lookup(this.var);
        if(!varVal.getType().equals(new IntType()))
            throw new StatementException("Variable's value is not of type int");

        int foundIndex = ((IntValue) varVal).getValue();

        if(!semTable.exists(foundIndex))
            throw new StatementException("Index does not exist");

        Triplet triplet = semTable.lookup(foundIndex);
        if(triplet.first - triplet.third > triplet.second.size()){
            if(!triplet.second.contains(state.getId_thread()))
                triplet.second.add(state.getId_thread());
        } else
            stack.push(this);

        state.setExeStack(stack);
        state.setToySemaphoreTable(semTable);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new AquireToyStatement(this.var);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        return table;
    }

    @Override
    public String toString() {
        return "ToyAcquire(" + this.var + ")";
    }
}

