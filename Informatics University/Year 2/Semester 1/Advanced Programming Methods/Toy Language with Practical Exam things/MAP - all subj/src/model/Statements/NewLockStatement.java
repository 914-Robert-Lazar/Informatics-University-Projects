package model.Statements;

import exceptions.AdtException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyLockTableInterface;
import model.ADTs.MyStackInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.IntType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.Value;

public class NewLockStatement implements StatementInterface{
    private final String varName;

    public NewLockStatement(String var) {
        this.varName = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyLockTableInterface<Integer> lockTable = state.getLockTable();

        if(symbolTable.isDefined(this.varName)){
            Value varValue = symbolTable.lookup(this.varName);
            if(varValue.getType().equals(new IntType())) {
                int newFreeLocation = lockTable.allocate(-1);
                symbolTable.update(this.varName, new IntValue(newFreeLocation));
            } else throw new StatementException("The value associated with the variable from newLock is not of type int");
        } else throw new StatementException("the variable in new lock statement is not in symbol table.");

        state.setSymbolTable(symbolTable);
        state.setLockTable(lockTable);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new NewLockStatement(this.varName);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type varType = table.lookup(this.varName);
        if(varType.equals(new IntType())){
            return table;
        } else throw new TypeException("Variable not of type int");
    }

    @Override
    public String toString(){
        return "newLock(" + this.varName + ")";
    }
}
