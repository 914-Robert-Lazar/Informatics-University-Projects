package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ADTs.MyCountDownLatchTableInterface;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.IntType;
import model.Types.Type;
import model.Values.IntValue;
import model.Values.Value;

public class NewLatchStatement implements StatementInterface{
    private final String varName;
    private final Expression exp;

    public NewLatchStatement(String varName, Expression exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyHeapInterface<Value> heap = state.getHeap();
        MyCountDownLatchTableInterface<Integer> latchTable = state.getLatchTable();

        Value expressionValue = this.exp.eval(symbolTable, heap);
        if(!expressionValue.getType().equals(new IntType()))
            throw new StatementException("value of expression is not int (Latch)");

        IntValue num1 = (IntValue) expressionValue;

        if(symbolTable.isDefined(this.varName)){
            if(symbolTable.lookup(this.varName).getType().equals(new IntType())){
                int newFreeLocation = latchTable.allocate(num1.getValue());
                symbolTable.update(this.varName, new IntValue(newFreeLocation));
            } else throw new StatementException("Variable has not the type int");
        } else throw new StatementException("Variable name not defined in symbol table (Latch)");

        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new NewLatchStatement(this.varName, this.exp);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type varType = table.lookup(this.varName);
        Type exprType = this.exp.typecheck(table);
        if(varType.equals(new IntType()) && exprType.equals(new IntType())) {
            return table;
        } else throw new TypeException("Var or expression or both are not of type int");
    }

    @Override
    public String toString() {
        return "newLatch(" + this.varName + "," + this.exp.toString() + ")";
    }
}
