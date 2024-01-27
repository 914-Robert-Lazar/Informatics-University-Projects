package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements Statement{
    private Expression expression;
    public CloseReadFileStatement(Expression ex){
        expression=ex;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Value value=expression.eval(state.getSymbolsTable(),state.getHeapTable());
        if (!(value instanceof StringValue)){
            throw new InterpreterException("Value is not of StringValue");
        }
        if(!(state.getFileTable().isDefined((StringValue) value))){
            throw new InterpreterException("BufferReader is not defined");
        }
        BufferedReader buf=state.getFileTable().lookup((StringValue) value);
        try{
            buf.close();
            state.getFileTable().delete((StringValue) value);
        }catch (IOException ie){
            throw new InterpreterException("cannot close file");
        }
        return null;
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new CloseReadFileStatement(expression.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type expressionType= expression.typeCheck(typeEnvironment);
        if (expressionType.equals(new StringType()))
            return typeEnvironment;
        else
            throw new InterpreterException("Expression is not of type string");
    }

    @Override
    public String toString() {
        return "CloseFile("+expression+");";
    }
}
