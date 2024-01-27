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
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFileStatement implements Statement{
    private Expression expression;

    public OpenReadFileStatement(Expression expression){
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Value value=expression.eval(state.getSymbolsTable(), state.getHeapTable());
        if (!(value instanceof StringValue)){
            throw new InterpreterException("Invalid Value Type for the Expression");
        }
        if (state.getFileTable().isDefined((StringValue) value)){
            throw new InterpreterException("File already opened");
        }
        BufferedReader br;
        try{

            br=new BufferedReader(new FileReader(((StringValue) value).getValue()));
        }catch (IOException ie){
            throw new InterpreterException(ie.toString());
        }
        state.getFileTable().update((StringValue) value,br);
        return null;
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new OpenReadFileStatement(expression.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type expressionType=expression.typeCheck(typeEnvironment);
        if (expressionType.equals(new StringType()))
            return typeEnvironment;
        else
            throw new InterpreterException("OpenFile statement: Expression is not of type string");
    }

    @Override
    public String toString() {
        return "OpenReadFile("+expression+")"+";";
    }
}
