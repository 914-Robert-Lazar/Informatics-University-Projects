package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyLockTableInterface;
import model.ADTs.MyStackInterface;
import model.ProgramState;
import model.Types.IntType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.Value;

public class UnlockStatement implements StatementInterface{
    private final String varName;

    public UnlockStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyStackInterface<StatementInterface> stack = state.getExeStack();
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyLockTableInterface<Integer> lockTable = state.getLockTable();

        if(symbolTable.isDefined(this.varName)) {
            Value foundIndex = symbolTable.lookup(this.varName);
            if(foundIndex.getType().equals(new IntType())){
                IntValue foundIndexInt = (IntValue) foundIndex;
                if(lockTable.exists(foundIndexInt.getValue())){
                    if(lockTable.lookup(foundIndexInt.getValue()).equals(state.getId_thread()))
                        lockTable.update(foundIndexInt.getValue(), -1);
                }else throw new StatementException("Lock does not exist");
            } else throw new StatementException("Variable: " + this.varName + "has a value that is not int");
        } else throw new StatementException("Variable is not in symbol table(lock statement)");

        state.setExeStack(stack);
        state.setSymbolTable(symbolTable);
        state.setLockTable(lockTable);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new UnlockStatement(this.varName);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type varType = table.lookup(this.varName);
        if(varType.equals(new IntType())){
            return table;
        } else throw new TypeException("Variable not of type int");
    }

    @Override
    public String toString() {
        return "unlock (" + this.varName + ")";
    }
}
