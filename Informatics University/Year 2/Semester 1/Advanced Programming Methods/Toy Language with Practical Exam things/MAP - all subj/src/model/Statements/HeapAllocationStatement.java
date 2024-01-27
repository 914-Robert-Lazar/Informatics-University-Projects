package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.RefType;
import model.Types.Type;
import model.Values.RefValue;
import model.Values.Value;

public class HeapAllocationStatement implements StatementInterface{
    String varName;
    Expression expReplacement;

    public HeapAllocationStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expReplacement = expression;
    }

    @Override
    public String toString() {
        return "new(" + this.varName + ',' + this.expReplacement.toString() + ')';
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyHeapInterface<Value> heap = state.getHeap();

        if (!symbolTable.isDefined(this.varName))
            throw new StatementException("Variable " + this.varName + " is not defined in symbolTable!");
        Value refVal = symbolTable.lookup(this.varName);
        if (!(refVal.getType() instanceof RefType))
            throw new StatementException("The value from SymbolTable doesn't have the type RefType!");

        Value newValue = this.expReplacement.eval(symbolTable, heap);
        if(!(newValue.getType().equals(((RefType) refVal.getType()).getInner())))
            throw new StatementException("New expression's value type is not the same as the variable");

        int addr = heap.allocate(newValue);
        symbolTable.update(this.varName, new RefValue(addr, newValue.getType()));

        state.setSymbolTable(symbolTable);
        state.setHeap(heap);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapAllocationStatement(this.varName, this.expReplacement);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws InterpreterException {
        Type variableType = table.lookup(this.varName);
        Type expressionType = this.expReplacement.typecheck(table);
        if (variableType.equals(new RefType(expressionType))) {
            return table;
        } else {
            throw new TypeException("Different types on heap allocation");
        }
    }
}
