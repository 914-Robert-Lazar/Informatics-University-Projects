package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Triplet;
import model.Types.IntType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.Value;

import java.util.LinkedList;

public class NewToySemaphoreStatement implements StatementInterface{
    private final String var;
    private final Expression exp1;
    private final Expression exp2;

    public NewToySemaphoreStatement(String var, Expression exp1, Expression exp2) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symTable = state.getSymbolTable();
        MyHeapInterface<Value> heap = state.getHeap();


        Value exp1Val = this.exp1.eval(symTable, heap);
        Value exp2Val = this.exp2.eval(symTable, heap);

        if(!exp1Val.getType().equals(new IntType()) || !exp2Val.getType().equals(new IntType()))
            throw new StatementException("Values of expressions are not int");

        if(!symTable.isDefined(this.var))
            throw  new StatementException("Variable not defined");

        Value varVal = symTable.lookup(this.var);
        if(!varVal.getType().equals(new IntType()))
            throw new StatementException("Variable not int");

        int exp1Int = ((IntValue) exp1Val).getValue();
        int exp2Int = ((IntValue) exp2Val).getValue();
        int newFreeLocation = state.getToySemaphoreTable().allocate(new Triplet(exp1Int, new LinkedList<>(), exp2Int));
        symTable.update(this.var, new IntValue(newFreeLocation));

        state.setSymbolTable(symTable);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new NewToySemaphoreStatement(this.var, this.exp1, this.exp2);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        return table;
    }

    @Override
    public String toString() {
        return "newToySemaphore (" + this.var + ", " + this.exp1.toString() + ", " + this.exp2.toString() + ")";
    }
}
