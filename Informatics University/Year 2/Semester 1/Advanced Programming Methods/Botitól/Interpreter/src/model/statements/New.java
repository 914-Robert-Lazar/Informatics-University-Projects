package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class New implements Statement{
    private String name;
    private Expression expression;
    public New(String name,Expression expression){
        this.expression=expression;
        this.name=name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        if (state.getSymbolsTable().isDefined(name)){
            if (state.getSymbolsTable().lookup(name).getType() instanceof ReferenceType){
                Value value=expression.eval(state.getSymbolsTable(),state.getHeapTable());
                ReferenceValue value2= (ReferenceValue) state.getSymbolsTable().lookup(name);
                if (value2.getLocationType().equals(value.getType())){
                    Integer pos=state.getHeapTable().add(value);
                    state.getSymbolsTable().update(name,new ReferenceValue(pos,value2.getLocationType()));
                }else{
                    throw new InterpreterException("The type of the expression and the type of the associated name does not match");
                }
            }else{
                throw new InterpreterException("Value is not of type ReferenceType");
            }
        }else{
            throw new InterpreterException("Name is not defined");
        }
        return null;
    }

    @Override
    public String toString() {
        return "new("+name+","+expression+");";
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new New(name,expression.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type variableType=typeEnvironment.lookup(name);
        Type expressionType=expression.typeCheck(typeEnvironment);
        if (variableType.equals(new ReferenceType(expressionType)))
            return typeEnvironment;
        else
            throw new InterpreterException("New statement: the left hand and right hand does not have the same type");
    }
}
