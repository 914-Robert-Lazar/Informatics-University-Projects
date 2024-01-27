package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import javafx.util.Pair;
import model.ADTs.MyCountSemaphoreInterface;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.IntType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.Value;

import java.util.LinkedList;
import java.util.List;

public class CreateCountSemaphoreStatement implements StatementInterface{
    private final String varName;
    private final Expression exp;

    public CreateCountSemaphoreStatement(String varName, Expression exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyHeapInterface<Value> heap = state.getHeap();
        MyCountSemaphoreInterface<Pair<Integer, List<Integer>>> semTable = state.getCountSemaphoreTable();

        Value expVal = this.exp.eval(symbolTable, heap);
        if(!expVal.getType().equals(new IntType()))
            throw new StatementException("Expression value is not int");

        IntValue number1 = (IntValue) expVal;

        if(!symbolTable.isDefined(this.varName))
            throw new StatementException("Variable not in symbol table");
        Value varVal = symbolTable.lookup(this.varName);
        if(!varVal.getType().equals(new IntType()))
            throw new StatementException("Variable value not of type int");

        int newfreelocation = semTable.allocate(new Pair<>(number1.getValue(), new LinkedList<>()));
        symbolTable.update(this.varName, new IntValue(newfreelocation));

        state.setSymbolTable(symbolTable);
        state.setCountSemaphoreTable(semTable);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new CreateCountSemaphoreStatement(this.varName, this.exp);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type varType = table.lookup(this.varName);
        Type expType = this.exp.typecheck(table);

        if(varType.equals(new IntType()) && expType.equals(new IntType()))
            return table;
        else throw new TypeException("Types are not integer");
    }

    @Override
    public String toString(){
        return "createCountSemaphore(" + this.varName + "," + this.exp.toString() + ")";
    }
}
