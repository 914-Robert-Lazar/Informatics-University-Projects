package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ADTs.MyCountDownLatchTableInterface;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.ADTs.MyStackInterface;
import model.ProgramState;
import model.Types.IntType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.Value;

public class AwaitStatement implements StatementInterface{
    private final String varName;

    public AwaitStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyStackInterface<StatementInterface> stack = state.getExeStack();
        MyCountDownLatchTableInterface<Integer> latchTable = state.getLatchTable();

        if(!symbolTable.isDefined(this.varName))
            throw new StatementException("Variable not defined in symbol table (await stmt)");
        Value foundIndex = symbolTable.lookup(this.varName);
        if(!foundIndex.getType().equals(new IntType()))
            throw new StatementException("Variable not of type int");

        int foundIndexInt = ((IntValue) foundIndex).getValue();
        if(!latchTable.exists(foundIndexInt))
            throw new StatementException("Variable not found in latch table.");
        else if (latchTable.lookup(foundIndexInt) != 0)
            stack.push(this);

        state.setExeStack(stack);
        state.setSymbolTable(symbolTable);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new AwaitStatement(this.varName);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type varType = table.lookup(this.varName);
        if(varType.equals(new IntType()))
            return table;
        else
            throw new TypeException("Variable not of type int.");
    }

    @Override
    public String toString() {
        return "await(" + this.varName + ")";
    }
}
