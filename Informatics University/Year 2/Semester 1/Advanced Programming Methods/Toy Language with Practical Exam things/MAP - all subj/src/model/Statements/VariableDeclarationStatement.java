package model.Statements;

import exceptions.AdtException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.ADTs.MyDictionaryInterface;
import model.ProgramState;
import model.Types.Type;
import model.Values.Value;


public class VariableDeclarationStatement implements StatementInterface{
    private final String name;
    private final Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, AdtException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        if(symbolTable.isDefined(name))
            throw new StatementException("Variable is already declared");
        else{
            symbolTable.add(name, type.getDefault());
        }
        state.setSymbolTable(symbolTable);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new VariableDeclarationStatement(this.name, this.type);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        table.add(name, type);
        return table;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
