package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class HeapWritingStatement implements Statement{
    private String name;
    private Expression expression;
    public HeapWritingStatement(String name, Expression expression){
        this.name=name;
        this.expression=expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        if (state.getSymbolsTable().isDefined(name)){
            if (state.getSymbolsTable().lookup(name).getType() instanceof ReferenceType){
                ReferenceValue val= (ReferenceValue)state.getSymbolsTable().lookup(name);
                if (state.getHeapTable().isDefined(val.getAddress())){
                    Value value=expression.eval(state.getSymbolsTable(), state.getHeapTable());
                    if (value.getType().equals(val.getLocationType())){
                        state.getHeapTable().update(val.getAddress(),value);
                    }else{
                        throw new InterpreterException("The types do not match");
                    }
                }else{
                    throw new InterpreterException("Address is not defined in the heap table");
                }
            }else{
                throw new InterpreterException("Type associated is not ReferenceType");
            }
        }else {
            throw new InterpreterException("Name is not defined");
        }
        return null;
    }

    @Override
    public String toString() {
        return "WriteHeap("+name+","+expression+");";
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new HeapWritingStatement(name,expression.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type variableType=typeEnvironment.lookup(name);
        Type expressionType=expression.typeCheck(typeEnvironment);
        if (variableType.equals(new ReferenceType(expressionType)))
            return typeEnvironment;
        else
            throw new InterpreterException("HeapWriting statement: left hand side and right hand side does not have the same type");
    }
}
